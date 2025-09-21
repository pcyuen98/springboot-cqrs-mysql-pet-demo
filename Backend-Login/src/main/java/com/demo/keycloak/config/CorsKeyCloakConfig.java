package com.demo.keycloak.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsKeyCloakConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry cors) {
        cors.addMapping("/**") // allow all paths
            .allowedOrigins("*") // allow all origins
            .allowedMethods("*") // allow all HTTP methods
            .allowedHeaders("*"); // allow all headers
    }
}
