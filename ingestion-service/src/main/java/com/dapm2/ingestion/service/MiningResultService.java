package com.dapm2.ingestion.service;

import com.dapm2.ingestion.dto.MiningResultDTO;
import com.dapm2.ingestion.entity.MiningResult;
import com.dapm2.ingestion.repository.MiningResultRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MiningResultService {

    private final MiningResultRepository repository;

    public MiningResultService(MiningResultRepository repository) {
        this.repository = repository;
    }

    public MiningResult saveResult(MiningResultDTO dto) {
        MiningResult result = new MiningResult();

        result.setResult(dto.getResult());
        result.setCreatedAt(LocalDateTime.now());
        return repository.save(result);
    }
}
