package com.dapm2.sink.dto;

import lombok.*;
import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MiningResultDTO {
    private Long id;
    private String caseid;
    private String activity;
    private Instant timestamp;
    private String fromActivity;
    private String toActivity;
    private Long frequency;
    private Double dependency;
    /** Parsed extra attributes */
    private Map<String, Object> attributes;
}
