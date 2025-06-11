//// ── src/main/java/com/dapm2/sink/service/MiningResultService.java ──
//package com.dapm2.sink.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import communication.message.impl.event.Event;
//import com.dapm2.sink.entity.MiningResult;
//import com.dapm2.sink.repository.MiningResultRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class MiningResultService {
//
//    private final MiningResultRepository repository;
//
//    @Autowired
//    public MiningResultService(MiningResultRepository repository) {
//        this.repository = repository;
//    }
//    public String saveResult(Event eventReceived) {
//        try {
//            // Convert the full Event object to JSON string
//            String eventJson = String.format(
//                    "{\"caseID\":\"%s\",\"activity\":\"%s\",\"timestamp\":\"%s\"}",
//                    eventReceived.getCaseID(),
//                    eventReceived.getActivity(),
//                    eventReceived.getTimestamp().toString(),
//                    eventReceived.getAttributes()
//            );
//            try {
//                eventJson = new ObjectMapper().writeValueAsString(eventReceived);
//            } catch (JsonProcessingException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            MiningResult result = new MiningResult();
//            result.setResult(eventJson);
//            result.setCreatedAt(LocalDateTime.now());
//            MiningResult saved = repository.save(result);
//            return (saved != null && saved.getId() != null) ? "OK" : "ERROR";
//        } catch (Exception ex) {
//            System.err.println("MiningResultService.saveResult error → " + ex.getMessage());
//            ex.printStackTrace(System.err);
//            return "ERROR";
//        }
//    }
//}
