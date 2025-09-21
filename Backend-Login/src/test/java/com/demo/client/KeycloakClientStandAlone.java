package com.demo.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.demo.keycloak.exceptions.DemoAppException;
import com.demo.keycloak.utility_classes.ResponseEntityUtil;

public class KeycloakClientStandAlone {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakClientStandAlone.class);
    private static final Properties PROPERTIES = loadProperties();

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = KeycloakClientStandAlone.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find application.properties");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
        return props;
    }

    public static ResponseEntity<Map<String, Object>> login() {
        String keycloakUrl = PROPERTIES.getProperty("app.keycloakUrl");
        String clientId = PROPERTIES.getProperty("app.clientId", "angularfront");
        String username = PROPERTIES.getProperty("app.username", "keycloak");
        String password = PROPERTIES.getProperty("app.password", "password");
        String scope = PROPERTIES.getProperty("app.scope", "openid profile email");

        logger.info("Attempting login to Keycloak at {}", keycloakUrl);

        HttpEntity<MultiValueMap<String, String>> request = buildRequest(username, password, clientId, scope);

        try {
            RestTemplate restTemplate = new RestTemplate();
            
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            	    keycloakUrl,
            	    HttpMethod.POST,
            	    request,
            	    new ParameterizedTypeReference<Map<String, Object>>() {}
            	);

            if (response.getStatusCode().is2xxSuccessful()) {
            	
				Map<String, Object> responseBody = response.getBody();
                logger.debug("Keycloak response: {}", responseBody);

                String accessToken = (String) responseBody.get("access_token");
                if (accessToken != null) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("message", "Login successful");
                    result.put("access_token", accessToken);
                    logger.info("accessToken = {}", accessToken);
                    return ResponseEntityUtil.getResponseEntity(result, HttpStatus.OK, null);
                } else {
                    return ResponseEntityUtil.getResponseEntity("Access token not found", HttpStatus.BAD_REQUEST,
                            new DemoAppException("Access token not found"));
                }
            } else {
    			return ResponseEntityUtil.getResponseEntity("Login failed", HttpStatus.NOT_FOUND, null);
            }

        } catch (Exception e) {
            logger.error("Exception during login: {}", e.getMessage());
            return ResponseEntityUtil.getResponseEntity("Login failed", HttpStatus.UNAUTHORIZED, e);
        }
    }

    private static HttpEntity<MultiValueMap<String, String>> buildRequest(String username, String password, String clientId, String scope) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);
        map.add("client_id", clientId);
        map.add("scope", scope);

        return new HttpEntity<>(map, headers);
    }

    public static void main(String[] args) {
        ResponseEntity<Map<String, Object>> response = login();
        logger.info("Login Response: {}", response);
    }
}
