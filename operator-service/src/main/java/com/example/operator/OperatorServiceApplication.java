package com.example.operator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import repository.TemplateRepository;
import templates.EventOperatorB;
import templates.HeuristicsMiner;
import templates.BehaviouralPatternsConformance;

@SpringBootApplication
@ComponentScan(basePackages = {"controller", "pipeline", "communication", "repository"})
public class OperatorServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OperatorServiceApplication.class, args);

        TemplateRepository templateRepository = context.getBean(TemplateRepository.class);
        templateRepository.storeTemplate("SimpleOperator", EventOperatorB.class);
        templateRepository.storeTemplate("HeuristicsMiner", HeuristicsMiner.class);
        templateRepository.storeTemplate("BehaviouralPatternsConformance", BehaviouralPatternsConformance.class);
        templateRepository.storeTemplate("DockerSink", templates.DockerSink.class);
        templateRepository.storeTemplate("MeanJoinSaver", templates.MeanJoinSaver.class);
    }

}
