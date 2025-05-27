package com.dapm2.ingestion.repository;

import com.dapm2.ingestion.entity.AnonymizationRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnonymizationRuleRepository extends JpaRepository<AnonymizationRule, Long> {
    Optional<AnonymizationRule> findFirstByDataSourceId(String dataSourceId);
}
