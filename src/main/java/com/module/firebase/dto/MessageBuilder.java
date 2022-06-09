package com.module.firebase.dto;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.Notification.Builder;
import java.util.List;

public enum MessageBuilder {

	MS_MART(Notification.builder()
		.setImage("https://www.mseshop.co.kr/static/admin/images/layout/header-logo.png")),
	MS_WDC(Notification.builder()
		.setImage("https://www.mseshop.co.kr/static/admin/images/layout/header-logo.png"));

	private final Builder notificationMessageBuilder;

	MessageBuilder(Builder notificationMessageBuilder) {
		this.notificationMessageBuilder = notificationMessageBuilder;
	}

	public MessageBuilder title(String title) {
		notificationMessageBuilder.setTitle(title);
		return this;
	}

	public MessageBuilder body(String title) {
		notificationMessageBuilder.setBody(title);
		return this;
	}

	public MessageBuilder changeImage(String title) {
		notificationMessageBuilder.setImage(title);
		return this;
	}

	public Message.Builder token(String token) {
		return Message.builder()
			.setNotification(notificationMessageBuilder.build())
			.setToken(token);
	}

	public MulticastMessage.Builder tokens(List<String> tokens) {
		return MulticastMessage.builder()
			.setNotification(notificationMessageBuilder.build())
			.addAllTokens(tokens);
	}


}
