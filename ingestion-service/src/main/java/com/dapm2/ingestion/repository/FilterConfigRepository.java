package com.dapm2.ingestion.repository;

import com.dapm2.ingestion.entity.FilterConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilterConfigRepository extends JpaRepository<FilterConfig, Long> {}
