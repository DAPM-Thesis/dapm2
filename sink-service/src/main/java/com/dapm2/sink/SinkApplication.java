package com.dapm2.sink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import repository.TemplateRepository;
import templates.*;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dapm2.sink","controller", "pipeline", "communication", "repository"})
public class SinkApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SinkApplication.class, args);

        TemplateRepository templateRepository = context.getBean(TemplateRepository.class);
//        templateRepository.storeTemplate("SimpleSource", SourceA.class);
        templateRepository.storeTemplate("SimpleSink", SinkA.class);
//        templateRepository.storeTemplate("EventSource", EventSource.class);
//        templateRepository.storeTemplate("PetriNetSink", PetriNetSink.class);
//        templateRepository.storeTemplate("LanguageFilter", LanguageFilter.class);
    }
}
