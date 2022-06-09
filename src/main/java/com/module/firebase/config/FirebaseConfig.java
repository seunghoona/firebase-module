package com.module.firebase.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.module.firebase.service.NotificationResultInsertService;
import com.module.firebase.service.NotificationResultInsertServiceImpl;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class FirebaseConfig {

	private final ResourceLoader resourceLoader;

	public FirebaseConfig(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**
	 *
	 * File file = ResourceUtils.getFile("classpath:keystore/service-account.json");
	 * @throws IOException
	 */
	@PostConstruct
	public void init() throws IOException {
		Resource resource = resourceLoader.getResource("classpath:keystore/service-account.json");
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(resource.getInputStream());
		FirebaseOptions options = FirebaseOptions.builder()
			.setCredentials(googleCredentials)
			.build();

		FirebaseApp.initializeApp(options);
	}

	@Bean(name = "pushAsync")
	public Executor config() {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		ThreadPoolTaskExecutor threadPoolExecutor = new
			ThreadPoolTaskExecutor();
		threadPoolExecutor.setMaxPoolSize(availableProcessors * 2);
		threadPoolExecutor.setCorePoolSize(availableProcessors);
		threadPoolExecutor.setQueueCapacity(1000);
		threadPoolExecutor.setThreadNamePrefix("PUSH EXECUTOR:-");
		threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return threadPoolExecutor;
	}

	@Bean
	public NotificationResultInsertService notificationResultInsertService() {
		return new NotificationResultInsertServiceImpl();
	}
}
