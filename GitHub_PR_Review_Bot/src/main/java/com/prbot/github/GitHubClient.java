package com.prbot.github;

import com.prbot.config.GitHubProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@AllArgsConstructor
public class GitHubClient {
    private final RestTemplate restTemplate;
    private final GitHubProperties gitHubProperties;

    public String fetchPullRequestDiff(String repoFullName, Integer prNumber){
        try{
            String url = gitHubProperties.getApiUrl()
                    + "/repos/" + repoFullName
                    + "/pulls/" + prNumber;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer "+gitHubProperties.getApiToken());
            headers.set("Accept", "application/vnd.github.v3.diff");

            HttpEntity<Void> request = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

            return response.getBody();
        } catch (Exception e) {
            log.error("Call to pull PR failed due to ",e);
            return null;
        }
    }
}
