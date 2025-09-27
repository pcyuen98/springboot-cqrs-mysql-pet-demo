package com.demo.keycloak.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.demo.keycloak.model.bean.FeedbackDTO;
import com.demo.keycloak.model.bean.UserDTO;
import com.demo.keycloak.model.entity.FeedbackEntity;
import com.demo.keycloak.model.entity.UserEntity;
import com.demo.keycloak.model.entity.mapper.FeedbackSMapper;

class FeedbackMapperTest {

	private final FeedbackSMapper feedbackSMapper = Mappers.getMapper(FeedbackSMapper.class);
    
    @Test
    void toEntityTest() {
    	
    	UserDTO userDTO = new UserDTO();
    	userDTO.setId(10000l);
    	userDTO.setName("Mapper Name");
    	
    	FeedbackDTO feedbackDTO = new FeedbackDTO();
    	feedbackDTO.setUserDTO(userDTO);
    	feedbackDTO.setId(10000l);
    	FeedbackEntity feedbackEntity = feedbackSMapper.toEntity(feedbackDTO);
    	assertNotNull(feedbackEntity);
    	assertNotNull(feedbackEntity.getUsersEntity());
    	assertNotNull(feedbackEntity.getUsersEntity().getId());
    }
    
    @Test
    void toDTOTest() {
    	
    	UserEntity userEntity = new UserEntity();
    	userEntity.setId(10000l);
    	userEntity.setName("Mapper Name");
    	
    	FeedbackEntity feedbackEntity = new FeedbackEntity();
    	feedbackEntity.setUsersEntity(userEntity);
    	feedbackEntity.setId(10000l);
    	FeedbackDTO feedbackDTO = feedbackSMapper.toDto(feedbackEntity);
    	assertNotNull(feedbackDTO);
    	assertNotNull(feedbackDTO.getUserDTO());
    	assertNotNull(feedbackDTO.getUserDTO().getId());
    }
}
