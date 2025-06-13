//package com.dapm2.sink.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.time.OffsetDateTime;
//
///**
// * A JPA entity for storing exactly one Petri‐net snapshot per row.
// * Hibernate will auto‐create this table in the existing database.
// */
//@Entity
//@Getter
//@Setter
//@Table(name = "petri_snapshots")
//@NoArgsConstructor
//public class PetriSnapshot {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "snapshot_id")
//    private Long snapshotId;
//
//    /**
//     * Hibernate will set this to NOW() when the row is inserted.
//     */
//    @CreationTimestamp
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private OffsetDateTime createdAt;
//
//    /**
//     * Store the entire PetriNet as a JSONB string.
//     * Lombok will generate getter/setter; columnDefinition="jsonb" ensures JSONB type.
//     */
//    @Lob
//    @Column(name = "net_json", columnDefinition = "jsonb", nullable = false)
//    private String netJson;
//
//    public PetriSnapshot(String netJson) {
//        this.netJson = netJson;
//    }
//}
