package com.rest_api.fs14backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@OpenAPIDefinition(info = @Info(title = "Library System", version ="1.0.0"))
public class Fs14BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(Fs14BackendApplication.class, args);
	}

}
