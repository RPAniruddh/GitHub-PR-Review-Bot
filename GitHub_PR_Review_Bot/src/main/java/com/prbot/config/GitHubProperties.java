package com.prbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app.github")
public class GitHubProperties {
	private String webhookSecret;
	private String apiToken;
    private String apiUrl;
}
