package com.module.firebase.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.SendResponse;
import com.module.firebase.dto.MessageBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

	ApiFuture<BatchResponse> send(MessageBuilder messageBuilder, List<String> tokens);
	ApiFuture<BatchResponse> send(MessageBuilder messageBuilder, String ...tokens);


	CompletableFuture send(int i);
	String sendV2(int i);
}
