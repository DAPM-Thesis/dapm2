package com.dapm2.sink;

import templates.PetriNetSink;
import templates.SinkA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import repository.TemplateRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dapm2.sink","controller", "pipeline", "communication", "repository"})
public class SinkApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SinkApplication.class, args);
        TemplateRepository templateRepository = context.getBean(TemplateRepository.class);
        templateRepository.storeTemplate("SimpleSink", SinkA.class);
        templateRepository.storeTemplate("PetriNetSink", PetriNetSink.class);

    }
}
