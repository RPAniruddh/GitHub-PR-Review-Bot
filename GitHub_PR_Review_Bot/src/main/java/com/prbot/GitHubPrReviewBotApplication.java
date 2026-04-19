package com.prbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GitHubPrReviewBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitHubPrReviewBotApplication.class, args);
	}

}
