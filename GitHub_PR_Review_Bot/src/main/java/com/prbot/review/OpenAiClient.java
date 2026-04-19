package com.prbot.review;

import com.prbot.config.OpenAiProperties;
import com.prbot.dto.ReviewFeedback;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class OpenAiClient {
    private final RestTemplate restTemplate;
    private final OpenAiProperties openAiProperties;
    private final ObjectMapper objectMapper;

    public List<ReviewFeedback> getReview(String prompt){
        try{
            Map<String, Object> requestBody = new HashMap<>();

            requestBody.put("model", openAiProperties.getModel());
            requestBody.put("messages", List.of(
                    Map.of("role", "user", "content", prompt)
            ));

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer "+ openAiProperties.getApiKey());
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response =  restTemplate.postForEntity(openAiProperties.getUrl(), request, String.class);

            String reponseBody = response.getBody();
            JsonNode root = objectMapper.readTree(reponseBody);
            String content = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            return objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(List.class, ReviewFeedback.class));

        } catch (Exception e) {
            log.error("Open Ai model call failed due to", e);
            return  List.of();
        }
    }
}
