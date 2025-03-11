package com.legaldocanalyser.analyser.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
public class DocumentAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentName;
    private String documentType;
    @Lob
    private String originalText;
    @Lob
    private String analysisResult;
    @Lob
    private String recommendations;
    private Date uploadedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(String analysisResult) {
        this.analysisResult = analysisResult;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Override
    public String toString() {
        return "DocumentAnalysis{" +
                "id=" + id +
                ", documentName='" + documentName + '\'' +
                ", documentType='" + documentType + '\'' +
                ", originalText='" + originalText + '\'' +
                ", analysisResult='" + analysisResult + '\'' +
                ", recommendations='" + recommendations + '\'' +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}
