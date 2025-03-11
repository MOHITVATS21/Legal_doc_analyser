package com.legaldocanalyser.analyser.repository;

import com.legaldocanalyser.analyser.entity.DocumentAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentAnalysisRepository extends JpaRepository<DocumentAnalysis, Long> {
}
