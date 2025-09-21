package com.demo.keycloak.model.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.demo.keycloak.exceptions.DemoAppException;

@Service
public class LoginService {

	@Value("${app.keycloakUrl}")
	private String keycloakUrl;

	public ResponseEntity<Map<String, Object>> login(String username, String password) {

		String clientId = "admin-cli";
		String scope = "openid profile email";

		// 1. Set up the request headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// 2. Set up the request body
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "password");
		map.add("username", username);
		map.add("password", password);
		map.add("client_id", clientId);
		map.add("scope", scope);

		ResponseEntity<Map<String, Object>> response;
		Map<String, Object> restfulResponse = new HashMap<>();
		try {
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
			RestTemplate restTemplate = new RestTemplate();

			@SuppressWarnings("unchecked")
			Class<Map<String, Object>> responseType = (Class<Map<String, Object>>) (Class<?>) Map.class;
			response = restTemplate.postForEntity(keycloakUrl, request, responseType);

		} catch (RuntimeException e) {
			throw new DemoAppException(e);
		}

		if (response.getStatusCode().is2xxSuccessful()) {
			Map<String, Object> responseBody = response.getBody();

			if (responseBody != null && responseBody.containsKey("access_token")) {
				String accessToken = (String) responseBody.get("access_token");
				restfulResponse.put("accessToken", accessToken);
				return new ResponseEntity<>(restfulResponse, HttpStatus.OK);
			} else {
				restfulResponse.put("error", "Access token not found in response");
				return new ResponseEntity<>(restfulResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			restfulResponse.put("error", "Authentication failed");
			return new ResponseEntity<>(restfulResponse, HttpStatus.UNAUTHORIZED);
		}
	}
}
