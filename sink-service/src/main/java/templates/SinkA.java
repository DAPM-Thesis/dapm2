package templates;

import com.dapm2.sink.entity.MiningResult;
import com.dapm2.sink.repository.MiningResultRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import communication.message.Message;
import communication.message.impl.event.Attribute;
import communication.message.impl.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pipeline.processingelement.Configuration;
import pipeline.processingelement.Sink;
import utils.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.dapm2.sink.utils.AppConstants.*;
@Component
public class SinkA extends Sink {
    @Autowired
    private MiningResultRepository repository;

    public SinkA(Configuration configuration) {
        super(configuration);
    }
    @Override
    public void observe(Pair<Message, Integer> messageAndPortNumber) {
        System.out.println("SinkA!!");
        Event e = (Event) messageAndPortNumber.first();
        System.out.println(this + " received: " + "  caseID:   " + e.getCaseID()+",  activity:   "
                + e.getActivity() +",  timestamp:" + e.getTimestamp()+ " on port " + messageAndPortNumber.second());
        // 2) Build whatever “payload” string you want to store in DB

        MiningResult miningResult = setResultAttributes(e);
        repository.save(miningResult);
        // 4) Print to console as before
        System.out.println("Event Stored in DB");
    }

    private MiningResult setResultAttributes(Event e) {
        MiningResult miningResult = new MiningResult();
        miningResult.setCaseid(e.getCaseID());
        miningResult.setActivity(e.getActivity());
        miningResult.setTimestamp(Instant.parse(e.getTimestamp()));

        Attribute<?> miningAttr = e.getAttributes().stream()
                .filter(a -> "miningResult".equals(a.getName()))
                .findFirst()
                .orElse(null);
        if (miningAttr != null && miningAttr.getValue() instanceof Map) {
            Map<String, Attribute<?>> resultMap = (Map<String, Attribute<?>>) miningAttr.getValue();

            // Get value of specific keys
            Attribute<?> fromAttr = resultMap.get("hm_from");
            Attribute<?> toAttr = resultMap.get("hm_to");
            Attribute<?> freqAttr = resultMap.get("frequency");
            Attribute<?> dependencyAttr = resultMap.get("dependency");

            if (fromAttr != null) {
                miningResult.setFromActivity(fromAttr.getValue().toString());
                System.out.println("hm_from → " + fromAttr.getValue().toString());
            }

            if (toAttr != null) {
                miningResult.setToActivity(toAttr.getValue().toString());
                System.out.println("toAttr → " + toAttr.getValue().toString());
            }
            if (freqAttr != null) {
                miningResult.setFrequency(Long.parseLong(freqAttr.getValue().toString()));
                System.out.println("frequency → " + freqAttr);
            }
            if (dependencyAttr != null) {
                miningResult.setDependency(Double.parseDouble(dependencyAttr.getValue().toString()));
                System.out.println("dependencyAttr → " + dependencyAttr);
            }
        }
        Map<String, Object> attributeMap = new LinkedHashMap<>();

        for (Attribute<?> attr : e.getAttributes()) {
            if (!"miningresult".equalsIgnoreCase(attr.getName())) {
                attributeMap.put(attr.getName(), attr.getValue());
            }
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String attributesJson = objectMapper.writeValueAsString(attributeMap);
            miningResult.setAttributes(attributesJson);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace(); // or handle exception properly
        }
        return miningResult;
    }

    @Override
    protected Map<Class<? extends Message>, Integer> setConsumedInputs() {
        Map<Class<? extends Message>, Integer> map = new HashMap<>();
        map.put(Event.class, 1);
        return map;
    }
}
