package com.dapm2.sink.controller;

import com.dapm2.sink.dto.MiningResultDTO;
import com.dapm2.sink.entity.MiningResult;
import com.dapm2.sink.service.MiningResultService;
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
