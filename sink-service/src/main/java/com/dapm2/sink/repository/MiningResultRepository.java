package com.dapm2.sink.repository;

import com.dapm2.sink.entity.MiningResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiningResultRepository extends JpaRepository<MiningResult, Long> {
}
