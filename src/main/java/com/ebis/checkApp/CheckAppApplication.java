package com.ebis.checkApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CheckAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckAppApplication.class, args);
	}

}
