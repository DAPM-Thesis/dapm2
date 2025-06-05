package templates;

import com.dapm2.sink.service.MiningResultService;
import communication.message.Message;
import communication.message.impl.event.Event;
import pipeline.processingelement.Configuration;
import pipeline.processingelement.Sink;
import utils.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static com.dapm2.sink.utils.AppConstants.*;

public class SinkA extends Sink {

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
        String jsonPayload = "{"
                + "\"caseID\":\""    + e.getCaseID()    + "\","
                + "\"activity\":\""  + e.getActivity()  + "\","
                + "\"timestamp\":\"" + e.getTimestamp() + "\""
                + "}";
        // 3) Insert directly via JDBC
        //    (you might want to cache this PreparedStatement/Connection for performance)
        try (
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO mining_table (result, created_at) VALUES (?, ?)"
                )
        ) {
            ps.setString(1, jsonPayload);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
        }
        catch (Exception ex) {
            // Handle or log the exception; don’t let it kill your sink thread
            ex.printStackTrace();
        }

        // 4) Print to console as before
        System.out.println("Event Stored in DB");
    }

    @Override
    protected Map<Class<? extends Message>, Integer> setConsumedInputs() {
        Map<Class<? extends Message>, Integer> map = new HashMap<>();
        map.put(Event.class, 1);
        return map;
    }
}
