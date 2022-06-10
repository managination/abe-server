package com.example.abe;

import com.example.abe.model.Client;
import com.example.abe.service.ClientService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Attribute-based Encryption",
		version = "1.0.0",
		description = "Demo project which shows main functionality of Attribute-based Encryption technology"))
public class DemoAbeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAbeApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ClientService clientService) {
		return args -> {
//			Client maria = new Client("Maria", "maria5@gmail.com");
//			clientService.addNewClient(maria);
		};
	}

}
