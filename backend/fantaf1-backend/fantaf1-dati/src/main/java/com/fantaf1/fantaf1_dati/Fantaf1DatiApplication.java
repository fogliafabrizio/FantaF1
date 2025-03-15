package com.fantaf1.fantaf1_dati;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.fantaf1.fantaf1_dati")
@EnableScheduling
public class Fantaf1DatiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Fantaf1DatiApplication.class, args);
	}

}
