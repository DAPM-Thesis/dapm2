//package com.dapm2.sink.service;
//
//import com.dapm2.sink.entity.PetriSnapshot;
//import com.dapm2.sink.repository.PetriSnapshotRepository;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import communication.message.impl.petrinet.PetriNet;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.OffsetDateTime;
//
///**
// * A simple Spring Service that persists each MiningResult’s PetriNet
// * into the petri_snapshots table, with no raw SQL.
// */
//@Service
//public class PetriNetSavingService {
//
//    private final PetriSnapshotRepository repository;
//    private final ObjectMapper objectMapper;
//
//    @Autowired
//    public PetriNetSavingService(PetriSnapshotRepository repository) {
//        this.repository   = repository;
//        this.objectMapper = new ObjectMapper();
//    }
//
//    public void savePetriNet(PetriNet petriNet) {
//        try {
//            String netJson = objectMapper.writeValueAsString(petriNet);
//            // createdAt will be set automatically by Hibernate
//            PetriSnapshot snapshot = new PetriSnapshot(netJson);
//            repository.save(snapshot);
//            System.out.println("PetrinetSink save service successful!!!");
//        } catch (JsonProcessingException e) {
//            // In a real application you probably want to log this more gracefully
//            System.out.println("PetrinetSink save service failed!!!");
//            e.printStackTrace();
//        }
//    }
//}
