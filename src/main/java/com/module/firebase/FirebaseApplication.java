package com.module.firebase;

import com.google.firebase.messaging.Message;
import com.module.firebase.dto.MessageBuilder;
import com.module.firebase.service.NotificationService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class FirebaseApplication {

	@Autowired
	private NotificationService notificationService;

	public static void main(String[] args) {
		SpringApplication.run(FirebaseApplication.class, args);
	}

	@GetMapping("/send/token")
	public void sendToToken() {
		List<CompletableFuture<String>> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			CompletableFuture<String> send = notificationService.send(i);
			list.add(send);
		}
		List<String> collect = list.stream()
			.map(CompletableFuture::join)
			.collect(Collectors.toList());

		System.out.println("collect = " + collect);
		System.out.println(collect.size());
	}

	@GetMapping("/v2/send/token")
	public String 기다리지_않고_바로() {
		MessageBuilder body = MessageBuilder.MS_MART
			.title("test")
			.body("testBody");
		String s = "ez6UuW_nTgO0vNkEGu2u0m:APA91bFX_Uw1R02m75ig1g_pdvudMoJ1VDeK2M6cH2Q0qUuAL36VfIHockBNvZ28uM1CF8ThSYfKK-xv0ozcdHhs2Qd4AB3Ena2AUYl1Qoz5Oiaqzv4gHk9GL4o8YrFVrhwDjEkIl0gY";
		String[] strings = new String[500];
		Arrays.fill(strings, s);
		notificationService.send(body, List.of(strings));

		return "정상처리 되었습니다." + "3";
	}
}
