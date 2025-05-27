package com.dapm2.ingestion.repository;

import com.dapm2.ingestion.entity.AttributeSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeSettingRepository extends JpaRepository<AttributeSetting, Long> {
}
