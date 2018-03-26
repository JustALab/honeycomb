package com.honeycakesin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HoneycakesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoneycakesApplication.class, args);
	}
}
