package com.example.bird;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class BirdApplication {

	public static void main(String[] args) {
		SpringApplication.run(BirdApplication.class, args);
	}

}
