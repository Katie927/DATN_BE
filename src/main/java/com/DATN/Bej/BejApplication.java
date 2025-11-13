package com.DATN.Bej;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BejApplication {

	public static void main(String[] args) {
		SpringApplication.run(BejApplication.class, args);
	}

}
