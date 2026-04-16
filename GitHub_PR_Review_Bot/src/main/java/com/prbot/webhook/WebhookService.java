package com.prbot.webhook;

import org.springframework.stereotype.Service;

@Service
public class WebhookService {
	public void handleWebhook(String payload, String signature, String eventType, String deliveryId) {
	}
}
