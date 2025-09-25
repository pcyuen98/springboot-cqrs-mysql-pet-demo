package com.demo.keycloak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsKeyCloakConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // Allow your frontend origin(s)
        config.setAllowedOrigins(List.of("http://localhost:4200", "https://your-frontend.com"));
        
        // Allow credentials if needed (cookies, Authorization headers)
        config.setAllowCredentials(true);
        
        // Allow methods
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Allow headers
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        
        // Expose headers if needed
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        // Restrict only to your APIs
        source.registerCorsConfiguration("/demo/**", config);

        return new CorsFilter(source);
    }
}
