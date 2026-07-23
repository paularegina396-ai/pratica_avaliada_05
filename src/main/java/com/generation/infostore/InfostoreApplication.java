package com.generation.infostore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.generation.infostore")
public class InfostoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfostoreApplication.class, args);
	}

}
