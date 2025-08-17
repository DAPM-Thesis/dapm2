package templates;

import com.dapm2.ingestion.processingStages.*;
import communication.message.impl.event.Event;
import pipeline.processingelement.Configuration;
import pipeline.processingelement.source.SimpleSource;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import java.time.Duration;

public class SourceA extends SimpleSource<Event> {

    private String sseUrl;
    private FiltrationProcess filtrationProcess;
    private AnonymizationProcess anonymizationProcess;
    private AttributeMappingProcess attributeProcess;

    public SourceA(Configuration configuration) {
        super(configuration);
        sseUrl = EventSourceConfig.getEventSourceUrl(configuration);
        filtrationProcess = FiltrationProcess.getFilterConfig(configuration);
        anonymizationProcess = AnonymizationProcess.getAnonymizationConfig(configuration);
        attributeProcess = AttributeMappingProcess.getAttributeMappingConfig(configuration);

    }

    @Override
    protected Event process() {

        JsonNode json = WebClient.create()
                .get()
                .uri(sseUrl)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(JsonNode.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .blockFirst(Duration.ofSeconds(10));

        if (json == null) {
            throw new RuntimeException("Timed out waiting for a Wikipedia edit event");
        }

        if (!filtrationProcess.shouldPass(json)) {
            System.out.println("filtered out, pulling next =>>");
            System.out.println();
            return null;  // will cause SimpleSource to call process() again
        }

        json = anonymizationProcess.apply(json);

        Event dapmEvent = attributeProcess.extractEvent(json);
        String caseID = dapmEvent.getCaseID();
        String activity = dapmEvent.getActivity();
        String timestamp = dapmEvent.getTimestamp();
        System.out.println("Event Ingested:");
        System.out.println("caseID= " + caseID + ", activity= " + activity + ", timestamp= " + timestamp);
        System.out.println();
        return dapmEvent;
    }
}
