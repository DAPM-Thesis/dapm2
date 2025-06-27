package templates;

import com.dapm2.sink.influx.config.InfluxDBConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import communication.message.Message;
import communication.message.impl.event.Attribute;
import communication.message.impl.event.Event;
import org.springframework.stereotype.Component;
import pipeline.processingelement.Configuration;
import pipeline.processingelement.Sink;
import utils.Pair;

import java.time.Instant;
import java.util.*;

@Component
public class SinkA extends Sink {
    private final InfluxDBClient influxDBClient;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public SinkA(Configuration configuration) {
        super(configuration);
        influxDBClient = InfluxDBConfig.createClient();
    }
    @Override
    public void observe(Pair<Message, Integer> messageAndPortNumber) {
        System.out.println("SinkA!!");
        Event e = (Event) messageAndPortNumber.first();
        String miningMetricsPayload = null;
        for (Attribute<?> attr : e.getAttributes()) {
            if ("mining_metrics".equals(attr.getName())) {
                miningMetricsPayload = (String) attr.getValue();
                //System.out.println("Payload in sink: " + miningMetricsPayload);
                break;
            }
        }
        if (miningMetricsPayload == null) {
            System.out.println("No mining_metrics attribute found.");
            return;
        }
        // 2. Serialize ALL attributes as JSON (for full info backup)
        String allAttributesJson = "";
        try {
            allAttributesJson = objectMapper.writeValueAsString(e.getAttributes());
        } catch (Exception ex) {
            ex.printStackTrace();
            allAttributesJson = "{}";
        }

        // 3. Parse mining_metrics and write to InfluxDB
        try {
            List<Map<String, Object>> arcList = objectMapper.readValue(
                    miningMetricsPayload, new TypeReference<List<Map<String, Object>>>() {}
            );
            try (WriteApi writeApi = influxDBClient.getWriteApi()) {
                for (Map<String, Object> arc : arcList) {
                    String arcFrom = (String) arc.get("arc_from");
                    String arcTo = (String) arc.get("arc_to");
                    long frequency = ((Number) arc.get("frequency")).longValue();
                    double dependency = ((Number) arc.get("dependency")).doubleValue();
                    long timestamp = ((Number) arc.get("timestamp")).longValue();
                    String caseId = e.getCaseID();
                    String activity = e.getActivity();

                    Point point = Point.measurement("heuristic_miner_arcs")
                            .addTag("arc_from", arcFrom)
                            .addTag("arc_to", arcTo)
                            .addTag("wiki", caseId)
                            .addTag("type", activity)
                            .addField("frequency", frequency)
                            .addField("dependency", dependency)
                            .addField("all_attributes", allAttributesJson)
                            .time(Instant.ofEpochMilli(timestamp), WritePrecision.MS);

                    System.out.println("Writing to InfluxDB!!");
                    writeApi.writePoint(point);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Error parsing payload or writing to InfluxDB.");
        }
    }
    private String getAttributeValue(Set<Attribute<?>> attrs, String name) {
        return attrs.stream()
                .filter(a -> name.equals(a.getName()))
                .map(a -> a.getValue() == null ? "" : a.getValue().toString())
                .findFirst().orElse("");
    }
    @Override
    protected Map<Class<? extends Message>, Integer> setConsumedInputs() {
        Map<Class<? extends Message>, Integer> map = new HashMap<>();
        map.put(Event.class, 1);
        return map;
    }
    public void close() {
        influxDBClient.close();
    }
}
