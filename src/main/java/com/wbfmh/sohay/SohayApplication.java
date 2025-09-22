package com.wbfmh.sohay;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SohayApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load(); // This loads the .env file
		dotenv.entries().forEach(entry -> {
					if (System.getenv(entry.getKey()) == null)
						System.setProperty(entry.getKey(), entry.getValue());
				});

		SpringApplication.run(SohayApplication.class, args);
	}

}
