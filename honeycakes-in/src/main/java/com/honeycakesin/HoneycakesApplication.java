package com.honeycakesin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;

@EnableJpaAuditing
@SpringBootApplication
@EnableEmailTools
public class HoneycakesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoneycakesApplication.class, args);
	}
}
