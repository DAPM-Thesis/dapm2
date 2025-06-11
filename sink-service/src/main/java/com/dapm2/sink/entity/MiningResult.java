package com.dapm2.sink.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "mining_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MiningResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "caseid", nullable = false)
    private String caseid;

    @Column(name = "activity", nullable = false)
    private String activity;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    // "from" is a reserved word in SQL, so we map it explicitly to the column named "from"
    @Column(name = "hm_from", nullable = false)
    private String fromActivity;

    @Column(name = "hm_to", nullable = false)
    private String toActivity;

    @Column(name = "frequency", nullable = false)
    private Long frequency;

    @Column(name = "dependency", nullable = false)
    private Double dependency;

    /** all extra attributes serialized as JSON text */
    @Column(name = "attributes", columnDefinition = "TEXT")
    private String attributes;
}
