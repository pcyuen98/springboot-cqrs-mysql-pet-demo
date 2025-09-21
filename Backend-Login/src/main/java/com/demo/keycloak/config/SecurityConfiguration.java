package com.demo.keycloak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	private final JwtConverter jwtConverter;
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors().and().authorizeHttpRequests()
		        .requestMatchers("/demo/test/v1/redis/**").hasRole("redis")
				.requestMatchers("/demo/test/v1/keycloak**").hasRole("keycloak")
				.requestMatchers(HttpMethod.GET, "/user/**", "/demo/**").hasAuthority("SCOPE_profile")
				.requestMatchers(HttpMethod.POST, "/demo/**").hasAuthority("SCOPE_profile").anyRequest().authenticated()
				.and().oauth2ResourceServer().jwt();

		http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)));
		return http.build();
	}
}
