package com.demo.keycloak.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.demo.keycloak.model.bean.FeedbackDTO;
import com.demo.keycloak.model.bean.UserDTO;
import com.demo.keycloak.model.entity.UserEntity;
import com.demo.keycloak.model.entity.mapper.UserSMapper;
import com.demo.keycloak.model.service.FeedbackService;
import com.demo.keycloak.model.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
class FeedbackServiceTest {

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSMapper userSMapper;

	@Test
	@DisplayName("Save UserHistory")
	void saveUserHistory() {
		UserEntity userEntity = new UserEntity();
		userEntity.setName("last name");
		userEntity.setSurname("surname");
		userEntity.setUsername("username1");

		UserDTO userDTO = userService.save(userSMapper.toDto(userEntity));
		FeedbackDTO feedbackDTO = new FeedbackDTO();
		feedbackDTO.setUserDTO(userDTO);
		feedbackDTO = feedbackService.save(feedbackDTO);

		assertNotNull(feedbackDTO.getUserDTO());
		assertNotNull(feedbackDTO.getUserDTO().getId());

		userDTO = userService.findByUsername(feedbackDTO.getUserDTO().getUsername());
		assertNotNull(userDTO);
	}

}
