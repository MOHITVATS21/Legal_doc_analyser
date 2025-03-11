package com.legaldocanalyser.analyser.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.legaldocanalyser.analyser.entity.DocumentAnalysis;
import com.legaldocanalyser.analyser.repository.DocumentAnalysisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentAnalyzerService {
    @Autowired
    private AIAnalyzerService aiService;

    @Autowired
    private DocumentAnalysisRepository repository;

    public Map<String, Object> analyzeDocument(MultipartFile file) throws IOException {
        // Validate file
        if (!file.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("File must be a PDF");
        }
        if (file.getSize() > 10 * 1024 * 1024) { // 10 MB limit
            throw new IllegalArgumentException("File size must be less than 10 MB");
        }

        // Extract text from PDF
        String text = extractTextFromPdf(file);

        // Analyze with AI
        String aiResponse = aiService.analyzeLegalDocument(text);

        //System.out.print(aiResponse);
        // Save analysis
        DocumentAnalysis analysis = new DocumentAnalysis();
        analysis.setDocumentName(file.getOriginalFilename());
        analysis.setDocumentType(file.getContentType());
        analysis.setOriginalText(text);
        analysis.setAnalysisResult(aiResponse);
        analysis.setUploadedAt(new Date());
        repository.save(analysis);



        return parseAiResponse(aiResponse);
    }

    private String extractTextFromPdf(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (IOException e) {
            throw new IOException("Failed to extract text from PDF: " + e.getMessage(), e);
        }
    }

    private Map<String, Object> parseAiResponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);

            if (rootNode.isArray()) {
                String generatedText = rootNode.get(0).path("generated_text").asText();
                Map<String, Object> result = new HashMap<>();
                result.put("analysis", generatedText);
                return result;
            } else if (rootNode.has("error")) {
                throw new RuntimeException("API Error: " + rootNode.path("error").toString());
            }
            throw new RuntimeException("Unexpected response format");
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse AI response: " + e.getMessage(), e);
        }
    }

    public Map<String, Object> getAnalysisReport(String docId) {
        DocumentAnalysis analysis = repository.findById(Long.parseLong(docId)).orElseThrow();
        return parseAiResponse(analysis.getAnalysisResult());
    }

    public Map<String, Object> getRecommendations(String docId) {
        DocumentAnalysis analysis = repository.findById(Long.parseLong(docId)).orElseThrow();
        Map<String, Object> result = new HashMap<>();
        result.put("recommendations", analysis.getRecommendations() != null ? analysis.getRecommendations() : "No recommendations available");
        return result;
    }
}