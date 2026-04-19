package com.prbot.webhook;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.prbot.config.GitHubProperties;
import com.prbot.config.RedisConfig;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WebhookService {

	private final GitHubProperties gitHubProperties;
	private final RedisTemplate<String, String> redisTemplate;

	public WebhookService(RedisTemplate<String, String> redisTemplate, GitHubProperties gitHubProperties) {
		this.redisTemplate = redisTemplate;
		this.gitHubProperties = gitHubProperties;
	}

	public void handleWebhook(String payload, String signature, String eventType, String deliveryId) {
			if(! verifySignature(payload, signature)) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid signature");
			}
			if(isDuplicate(deliveryId)) {
				return;
			}
		log.info("Received webhook event: {} delivery: {}", eventType, deliveryId);
	}

	private boolean verifySignature(String payload, String signature) {
		try {
			String secret = gitHubProperties.getWebhookSecret();
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

			mac.init(secretKey);
			byte[] computedHash = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
			String expected ="sha256="+ HexFormat.of().formatHex(computedHash);

			return expected.equals(signature);
		} catch (Exception e) {
			log.error("verification failed due to ",e);
			return false;
		}
    }
	
	private boolean isDuplicate(String deliveryId) {
	    String redisKey = "webhook:" + deliveryId;
		Boolean exists = redisTemplate.hasKey(redisKey);

		if(Boolean.TRUE.equals(exists)) {
			return true;
		}
		redisTemplate.opsForValue().set(redisKey,"processed", 24, TimeUnit.HOURS);
		return false;
	}
}
