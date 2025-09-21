package com.demo.keycloak.controller;

import java.util.Locale;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.keycloak.model.bean.UserDTO;
import com.demo.keycloak.model.service.LoginService;
import com.demo.keycloak.model.service.UserService;
import com.demo.keycloak.utility_classes.ResponseEntityUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/demo/keycloak/v1")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

	private final LoginService loginService;
	
	private final UserService userService;

	private final ObjectMapper objectMapper;
    
	@Operation(summary = "Login and retrieve access token from Keycloak using username and password.",
			   description = "Authenticates the user with Keycloak and returns an access token based on provided source and destination parameters. Should use HTTP POST for security purposes instead of GET. Demo purposes only")
	@GetMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestParam("username") String username,
	                                                 @RequestParam("password") String password) {
		return loginService.login(username, password);
	}

    @Operation(
            summary = "Controller to test isAuthenticated tag",
            description = "Token must be provided to access. For testing and demo purposes only"
        )
        @PreAuthorize("isAuthenticated()")
        @GetMapping("/test")
        public String test() throws JsonProcessingException {
    		Object token = null;
            String tokenJson = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(token);

            log.info("Token --> {}", tokenJson);

            return """
                {
                  "message": "Token is Valid"
                }
                """;
        }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO user, Locale locale) {
    	UserDTO userDTO = userService.updateUser(user);
    	return ResponseEntityUtil.getResponseEntity(userDTO, HttpStatus.OK, null);
    }
}
