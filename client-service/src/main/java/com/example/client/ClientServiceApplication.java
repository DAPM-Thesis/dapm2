package com.example.client;

import candidate_validation.ValidatedPipeline;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import pipeline.PipelineBuilder;
import pipeline.service.PipelineExecutionService;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
@ComponentScan(basePackages = {"controller", "pipeline", "communication", "repository"})
public class ClientServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ClientServiceApplication.class, args);

        String pipelineID = "orgC_pipeline";
        String contents;
        try {
            contents = Files.readString(Paths.get("src/main/resources/multiple_PE_pipeline_with_config.json"));
            //contents = Files.readString(Paths.get("client-service/src/main/resources/demo-sse_multiple_PE_pipeline_with_config.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        URI configURI = Paths.get("src/main/resources/config_schemas").toUri();
        //URI configURI = Paths.get("client-service/src/main/resources/config_schemas").toUri();
        ValidatedPipeline validatedPipeline = new ValidatedPipeline(contents, configURI);

        PipelineBuilder pipelineBuilder = context.getBean(PipelineBuilder.class);
        pipelineBuilder.buildPipeline(pipelineID, validatedPipeline);

        PipelineExecutionService executionService = context.getBean(PipelineExecutionService.class);
        executionService.start(pipelineID);
    }
}
