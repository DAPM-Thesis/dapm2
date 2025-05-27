package com.dapm2.sink.service;

import com.dapm2.sink.dto.MiningResultDTO;
import com.dapm2.sink.entity.MiningResult;
import com.dapm2.sink.repository.MiningResultRepository;
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
