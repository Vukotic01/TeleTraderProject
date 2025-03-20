package com.example.projectteletrader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ProjectTeleTraderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectTeleTraderApplication.class, args);
	}

}
