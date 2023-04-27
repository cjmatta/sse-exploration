package com.github.cjmatta.sseexploration;

import com.github.cjmatta.sseexploration.config.KsqlDbProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(KsqlDbProperties.class)
public class SseExplorationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SseExplorationApplication.class, args);
	}

}
