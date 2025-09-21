package com.demo.keycloak.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    //@Value("${allowed.origins}")
    private String[] theAllowedOrigins = {"*"};

    //@Value("${spring.data.rest.base-path}")
    private String basePath = "/demo";

    @Override
    public void addCorsMappings(CorsRegistry cors) {
        cors.addMapping(basePath + "/**").allowedOrigins(theAllowedOrigins);
    }
}