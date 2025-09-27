package com.example.petstore.command.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.petstore.config.JwtConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Profile("!test")  // 👈 disables this config whenever the "test" profile is active
public class SecurityConfiguration {
	private final JwtConverter jwtConverter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.cors().and().authorizeHttpRequests()
	        // allow swagger-ui and api-docs without authentication
	        .requestMatchers(
	            "/swagger-ui/**",
	            "/swagger-ui.html",
	            "/v3/api-docs/**",
	            "/api-docs/**",
	            "/swagger-resources/**",
	            "/webjars/**"
	        ).permitAll()

	        // your existing security rules
	        .requestMatchers("/pet**").hasRole("admin")
	        .anyRequest().authenticated()
	        .and().oauth2ResourceServer().jwt();
	    http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));

	    return http.build();
	}

}
