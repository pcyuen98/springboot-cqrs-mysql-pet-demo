package com.demo.keycloak.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.keycloak.model.bean.FeedbackDTO;
import com.demo.keycloak.model.bean.UserDTO;
import com.demo.keycloak.model.service.FeedbackService;
import com.demo.keycloak.model.service.UserService;
import com.demo.keycloak.utility_classes.ResponseEntityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demo/keycloak/v1")
@PreAuthorize("isAuthenticated()")
@Slf4j
public class FeedbackController {

	private final FeedbackService feedbackService;
	
	private final UserService userService;

	@PostMapping("/feedback")
	public ResponseEntity<Map<String, Object>> updateFeedback(@RequestBody FeedbackDTO feedbackDTO) {

		UserDTO existingUser = userService.findByUsername(feedbackDTO.getUserDTO().getUsername());
		feedbackDTO.setUserDTO(existingUser);

		FeedbackDTO feedbackSaved = feedbackService.save(feedbackDTO);
		log.info("user feedback saved");
		return ResponseEntityUtil.getResponseEntity(feedbackSaved, HttpStatus.OK, null);
	}

}
