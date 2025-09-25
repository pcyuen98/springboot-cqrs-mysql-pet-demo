package com.example.petstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry cors) {
        cors.addMapping("/pet/**") // allow only /pet/* paths
            .allowedOrigins("http://localhost:4200") // ✅ specify frontend origin instead of "*"
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // restrict to needed HTTP methods
            .allowedHeaders("*"); // allow all headers
    }
}
