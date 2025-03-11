package com.legaldocanalyser.analyser.controller;

import com.legaldocanalyser.analyser.service.DocumentAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/legal-documents")
@CrossOrigin(origins = "*")
public class LegalDocumentController {


    private final DocumentAnalyzerService analyzerService;

    @Autowired
    public LegalDocumentController(DocumentAnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    // Upload legal document for analysis
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadDocument(@RequestParam("file")
                                                              MultipartFile file) {
        try {
            // Analyze the document and get the results
            Map<String, Object> analysisResult = analyzerService.analyzeDocument(file);
            return ResponseEntity.ok(analysisResult);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to analyze document. " + e.getMessage()));
        }
    }

    // Fetch a report of the analysis
    @GetMapping("/report/{docId}")
    public ResponseEntity<Map<String, Object>> getAnalysisReport(@PathVariable("docId") String docId) {
        try {
            // Get the analysis report by document ID
            Map<String, Object> report = analyzerService.getAnalysisReport(docId);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to retrieve report. " + e.getMessage()));
        }
    }

    // Fetch recommendations for legal clauses
    @GetMapping("/recommendations/{docId}")
    public ResponseEntity<Map<String, Object>>
    getRecommendations(@PathVariable("docId") String docId) {
        try {
            // Get recommendations for the document
            Map<String, Object> recommendations =
                    analyzerService.getRecommendations(docId);
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to retrieve recommendations. " + e.getMessage()));
        }
    }

}
