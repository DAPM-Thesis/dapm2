package com.dapm2.ingestion.repository;

import com.dapm2.ingestion.entity.MiningResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiningResultRepository extends JpaRepository<MiningResult, Long> {
}
