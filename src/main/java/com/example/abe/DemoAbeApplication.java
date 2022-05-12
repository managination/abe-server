package com.example.abe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoAbeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAbeApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ClientRepository clientRepository) {
		return args -> {
			Client maria = new Client("Maria", "maria5@gmail.com");
			clientRepository.save(maria);
		};
	}

}
