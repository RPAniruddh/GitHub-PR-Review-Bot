package com.prbot.webhook;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

	private final WebhookService webhookService;
	
	public WebhookController(WebhookService webhookService) {
	    this.webhookService = webhookService;
	}

	@PostMapping("/github")
	public ResponseEntity<Void> createHook(@RequestBody String payload,
			@RequestHeader("X-Hub-Signature-256") String signature, @RequestHeader("X-GitHub-Event") String eventType,
			@RequestHeader("X-GitHub-Delivery") String deliveryId) {
		webhookService.handleWebhook(payload, signature, eventType, deliveryId);
		return ResponseEntity.ok().build();

	}
}
