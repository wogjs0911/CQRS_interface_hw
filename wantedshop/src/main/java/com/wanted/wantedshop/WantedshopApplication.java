package com.wanted.wantedshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WantedshopApplication {
	public static void main(String[] args) {
		SpringApplication.run(WantedshopApplication.class, args);
	}
}
