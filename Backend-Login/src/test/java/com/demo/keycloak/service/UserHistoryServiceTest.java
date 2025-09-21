package com.demo.keycloak.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.demo.keycloak.model.bean.UserDTO;
import com.demo.keycloak.model.bean.UserHistoryDTO;
import com.demo.keycloak.model.entity.UserEntity;
import com.demo.keycloak.model.entity.mapper.UserSMapper;
import com.demo.keycloak.model.service.UserHistoryService;
import com.demo.keycloak.model.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
class UserHistoryServiceTest {

	@Autowired
	private UserHistoryService userHistoryService;

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
		userEntity.setUsername("saveUserHistory");
		
		UserDTO userDTO = userService.save(userSMapper.toDto(userEntity));
		UserHistoryDTO userHistoryDTO = new UserHistoryDTO();
		userHistoryDTO.setUserDTO(userDTO);
		userHistoryDTO = userHistoryService.save(userHistoryDTO);
		
		assertNotNull(userHistoryDTO.getUserDTO());
		assertNotNull(userHistoryDTO.getUserDTO().getId());
		
		userDTO = userService.findByUsername(userHistoryDTO.getUserDTO().getUsername());
		assertNotNull(userDTO);
	}

	@Test
	void updateUserTest() {
		
		UserEntity userEntity = new UserEntity();
		userEntity.setName("last name updateUserTest");
		userEntity.setSurname("surname updateUserTest");
		userEntity.setUsername("updateUserTest");
		
		UserDTO user = userService.save(userSMapper.toDto(userEntity));
		
		user = userService.updateUser(user);
		assertNotNull(user);
		
		UserDTO userDTO = userService.findByUsername(user.getUsername());
		assertNotNull(userDTO);
		assertNotNull(userDTO.getLastUpdatedDate());

	}

}
