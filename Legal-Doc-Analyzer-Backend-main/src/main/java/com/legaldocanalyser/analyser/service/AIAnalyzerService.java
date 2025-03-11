package com.legaldocanalyser.analyser.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class AIAnalyzerService {

    private final WebClient webClient;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    public AIAnalyzerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
    }

    public String analyzeLegalDocument(String text) {
        // Construct request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("inputs", "Please do the following for the provided legal document:\n" +
                "1. Identify and list high-risk clauses (e.g., terms that could lead to disputes, liabilities, etc.).\n" +
                "2. Identify and list ambiguous terms (e.g., terms that are vague or open to interpretation).\n" +
                "3. Highlight compliance issues (e.g., clauses violating regulatory laws or standards).\n" +
                "4. Provide mitigation recommendations for each identified issue.\n\n" +
                "Analyse the document on the above terms and provide the details less than 300 words" +
                "Document:\n" + text);


        // Add parameters (max_new_tokens = 250)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("max_new_tokens", 300);
        parameters.put("temperature", 0.8);
        requestBody.put("parameters", parameters);

        // Make the API call using WebClient
        return webClient.post()
                .uri(apiUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("API Error: " + errorBody)))
                )
                .bodyToMono(String.class)
                .block(); // Blocking call (can be replaced with reactive handling)
    }
}
