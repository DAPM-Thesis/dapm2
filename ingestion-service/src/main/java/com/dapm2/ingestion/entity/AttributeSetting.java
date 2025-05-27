package com.dapm2.ingestion.entity;

import com.dapm2.ingestion.utils.AppConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "attribute_setting")
@Getter
@Setter
public class AttributeSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "case_id", nullable = false)
    private String caseId;

    @Column(nullable = false)
    private String activity;

    @Column(name = "time_stamp", nullable = false)
    private String timeStamp;


    @Column(nullable = false)
    private String status = AppConstants.STATUS_ACTIVE;
}
