package com.example.petstore.command.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsCommandConfig implements WebMvcConfigurer {
	private String[] theAllowedOrigins = { "*" };

	private String basePath = "/api";

	@Override
	public void addCorsMappings(CorsRegistry cors) {
		cors.addMapping(basePath + "/**").allowedOrigins(theAllowedOrigins);
	}
}