package com.example.petstore.command.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.petstore.common.exception.DemoAppException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/demo/test/v1")
@RequiredArgsConstructor
@Slf4j
public class TestController {

	private final ApplicationContext applicationContext;
	static final String DEMO_MESSAGE = "DEMO Message";

	@GetMapping("/redis/role")
	public ResponseEntity<Object> isRoleRedis() {
		return ResponseEntity.ok(Collections.singletonMap(DEMO_MESSAGE, "Verified from BE. This user has a redis role"));
	}

	@GetMapping("/keycloak/role")
	public ResponseEntity<Object> isRoleUser() {
		return ResponseEntity
				.ok(Collections.singletonMap(DEMO_MESSAGE, "Verified from BE. This user has a keycloak role"));
	}

	@GetMapping("/test")
	public ResponseEntity<Object> test() {
		return ResponseEntity.ok(Collections.singletonMap(DEMO_MESSAGE, "Verified from BE. Token is valid"));
	}

	@GetMapping("/security/context")
	public ResponseEntity<Object> getSecurityContext() {

		return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}

	@GetMapping("/context/info")
	public ResponseEntity<Object> getContextInfo() {
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		List<String> beanLists = new ArrayList<>();
        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);
            if (bean.getClass().getPackageName().startsWith("com.demo")) {
            	beanLists.add(beanName);
            }
        }
		return ResponseEntity.ok(beanLists);
	}

	@GetMapping("/error/test")
	public ResponseEntity<Map<String, Object>> testGeneralError() {
		throw new DemoAppException("Just some Error Testing");
	}
}
