package com.module.firebase.service;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.SendResponse;
import com.module.firebase.dto.MessageBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationAsyncService implements NotificationService {

	private final FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
	private final NotificationResultInsertService notificationResultInsertService;

	public NotificationAsyncService(
		NotificationResultInsertService notificationResultInsertService) {
		this.notificationResultInsertService = notificationResultInsertService;
	}

	@Override
	public ApiFuture<BatchResponse> send(final MessageBuilder messageBuilder,
		final List<String> tokens) {

		if (tokens.isEmpty()) {
			throw new IllegalArgumentException("토큰 데이터가 없습니다.");
		}

		MulticastMessage multicastMessage = messageBuilder
			.tokens(tokens)
			.build();

		try {
			log.debug("전송 전 : " + Thread.currentThread().getName());
			ApiFuture<BatchResponse> batchResponseApiFuture = firebaseMessaging.sendMulticastAsync(
				multicastMessage);
			log.debug("전송 후 : " + Thread.currentThread().getName());
			return batchResponseApiFuture;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ApiFuture<BatchResponse> send(final MessageBuilder messageBuilder,
		final String... tokens) {
		return send(messageBuilder, List.of(tokens));
	}

	public CompletableFuture<String> send(int i) {
		// This registration token comes from the client FCM SDKs.
		String registrationToken = "ez6UuW_nTgO0vNkEGu2u0m:APA91bFX_Uw1R02m75ig1g_pdvudMoJ1VDeK2M6cH2Q0qUuAL36VfIHockBNvZ28uM1CF8ThSYfKK-xv0ozcdHhs2Qd4AB3Ena2AUYl1Qoz5Oiaqzv4gHk9GL4o8YrFVrhwDjEkIl0gY";

		// See documentation on defining a message payload.
		Message message = Message.builder()
			.setToken(registrationToken)
			.setAndroidConfig(AndroidConfig.builder()
				.setNotification(AndroidNotification.builder()
					.setTitle("제목")
					.setBody("내용")
					.setImage(
						"https://www.mseshop.co.kr/static/admin/images/layout/header-logo.png")
					.build())

				.build())
			.build();
		try {
			System.out.println(Thread.currentThread().getName());
			TimeUnit.SECONDS.sleep(3);
			var response = FirebaseMessaging.getInstance().send(message);
			return CompletableFuture.completedFuture(response);
		} catch (Exception e) {
			e.printStackTrace();
			return CompletableFuture.failedFuture(e);
		}
	}

	public String sendV2(int i) {

		String registrationToken = "ez6UuW_nTgO0vNkEGu2u0m:APA91bFX_Uw1R02m75ig1g_pdvudMoJ1VDeK2M6cH2Q0qUuAL36VfIHockBNvZ28uM1CF8ThSYfKK-xv0ozcdHhs2Qd4AB3Ena2AUYl1Qoz5Oiaqzv4gHk9GL4o8YrFVrhwDjEkIl0gY";

		Message message = Message.builder()
			.putData("title", "메시지를 보낼거야(" + i + ")")
			.putData("content", "너에게")
			.setToken(registrationToken)
			.build();

		try {
			System.out.println(Thread.currentThread().getName());
			TimeUnit.SECONDS.sleep(3);
			var response = FirebaseMessaging.getInstance().send(message);

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}


}
