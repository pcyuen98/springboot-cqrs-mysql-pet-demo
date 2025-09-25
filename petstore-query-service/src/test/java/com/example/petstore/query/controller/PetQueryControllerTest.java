package com.example.petstore.query.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.petstore.config.JwtConverter;
import com.example.petstore.query.service.PetQueryService;

@WebMvcTest(controllers = PetQueryController.class)
@ActiveProfiles("mvc-test") // 👈 separate profile for MVC tests
@Import(PetQueryControllerTest.NoSecurityConfig.class) // ✅ disable security only here
class PetQueryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetQueryService petQueryService;

	@MockBean
	private JwtConverter jwtConverter; // required by SecurityConfiguration, keep mocked

	/**
	 * ✅ Security disabled for MVC tests
	 */
	@TestConfiguration
	static class NoSecurityConfig {
		@Bean(name = "testFilterChain")
		public org.springframework.security.web.SecurityFilterChain filterChain(
				org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
			return http.csrf().disable().authorizeHttpRequests(auth -> auth.anyRequest().permitAll()).build();
		}
	}

	@Test
	void testGetAllPets() throws Exception {

		mockMvc.perform(get("/pet/all").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testGetPetById() throws Exception {

		mockMvc.perform(get("/pet/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testGetPetsByStatus() throws Exception {

		mockMvc.perform(get("/pet/findByStatus/available").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testSearchPets() throws Exception {

		mockMvc.perform(get("/pet/search").param("status", "AVAILABLE").param("data", "bird")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
}
