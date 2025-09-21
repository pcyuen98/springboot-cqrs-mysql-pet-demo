package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
	//	scanBasePackages = {"com.example.petstore.config",}
)
public class HTTPKeycloakApplication {

    public static void main(String[] args) {
        SpringApplication.run(HTTPKeycloakApplication.class, args);
    }

}