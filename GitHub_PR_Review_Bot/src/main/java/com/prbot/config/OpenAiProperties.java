package com.prbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app.openai")
public class OpenAiProperties {
	private String apiKey;
	private String model;
	private String url;
}
