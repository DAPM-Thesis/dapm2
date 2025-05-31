package templates;

import com.dapm2.ingestion.processingStages.AnonymizationProcess;
import com.dapm2.ingestion.processingStages.AttributeMappingProcess;
import com.dapm2.ingestion.processingStages.FiltrationProcess;
import communication.message.impl.event.Event;
import pipeline.processingelement.Configuration;
import pipeline.processingelement.source.SimpleSource;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import java.time.Duration;

public class SourceA extends SimpleSource<Event> {

    private static final String SSE_URL      = "https://stream.wikimedia.org/v2/stream/recentchange";
    private static final long   FILTERING_ID = 1L;
    private static final long   ATTRIBUTE_ID = 1L;
    private static final String SOURCE_ID    = "wiki-edit";

    private FiltrationProcess       filtrationProcess = FiltrationProcess.fromFilterId(FILTERING_ID);
    private AnonymizationProcess    anonymizationProcess = AnonymizationProcess.fromDataSourceId(SOURCE_ID);
    private AttributeMappingProcess attributeProcess = AttributeMappingProcess.fromSettingId(ATTRIBUTE_ID);

    public SourceA(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected Event process() {
        // lazy-init on first call
        if (filtrationProcess == null) {
            System.out.println("Lazy init of pipeline steps");
            this.filtrationProcess    = FiltrationProcess.fromFilterId(FILTERING_ID);
            this.anonymizationProcess = AnonymizationProcess.fromDataSourceId(SOURCE_ID);
            this.attributeProcess     = AttributeMappingProcess.fromSettingId(ATTRIBUTE_ID);
        }

        JsonNode json = WebClient.create()
                .get()
                .uri(SSE_URL)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(JsonNode.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .blockFirst(Duration.ofSeconds(10));

        if (json == null) {
            throw new RuntimeException("Timed out waiting for a Wikipedia edit event");
        }

        if (!filtrationProcess.shouldPass(json)) {
            System.out.println("filtered out, pulling next…");
            return null;  // will cause SimpleSource to call process() again
        }

        json = anonymizationProcess.apply(json);

        Event dapmEvent = attributeProcess.extractEvent(json);
        System.out.println("Event Ingested!!!");
        return dapmEvent;
    }
}
