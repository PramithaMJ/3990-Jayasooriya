package com.pmj.Jayasooriya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class Application {

	@GetMapping
	public String message() {
		return "EG/2020/3990 - Jayasooriya L.P.M.";
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
