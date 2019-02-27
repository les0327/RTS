package com.les;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.les.*")
public class RtsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RtsApplication.class, args);
	}

}