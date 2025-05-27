package com.dapm2.ingestion.controller;

import com.dapm2.ingestion.dto.MiningResultDTO;
import com.dapm2.ingestion.entity.MiningResult;
import com.dapm2.ingestion.service.MiningResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sink/mining-result")
public class MiningResultController {

    private final MiningResultService service;

    public MiningResultController(MiningResultService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<MiningResult> saveResult(@RequestBody MiningResultDTO dto) {

        System.out.println(" Controller reached!");
        return ResponseEntity.ok(service.saveResult(dto));

    }
}
