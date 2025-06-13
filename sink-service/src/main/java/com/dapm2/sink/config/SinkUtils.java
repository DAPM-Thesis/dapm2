//package com.dapm2.sink.config;
//
////import com.dapm2.sink.service.PetriNetSavingService;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
///**
// * Static helper so that non‐Spring classes (like PetriNetSink) can fetch beans.
// */
//@Component
//public class SinkUtils implements ApplicationContextAware {
//
//    private static ApplicationContext context;
//
//    @Override
//    public void setApplicationContext(ApplicationContext ctx) {
//        context = ctx;
//    }
//
//    public static PetriNetSavingService getPetriNetSavingService() {
//        return context.getBean(PetriNetSavingService.class);
//    }
//}
//
