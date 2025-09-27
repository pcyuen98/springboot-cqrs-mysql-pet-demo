package com.demo.keycloak.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.demo.keycloak.model.bean.UserDTO;
import com.demo.keycloak.model.bean.UserHistoryDTO;
import com.demo.keycloak.model.entity.UserEntity;
import com.demo.keycloak.model.entity.UserHistoryEntity;
import com.demo.keycloak.model.entity.mapper.UserHistorySMapper;

class UserHistoryMapperTest {

	private final UserHistorySMapper userHistorySMapper = Mappers.getMapper(UserHistorySMapper.class);
    
    @Test
    void toEntityTest() {

    	
    	UserDTO userDTO = new UserDTO();
    	userDTO.setId(10000l);
    	userDTO.setName("Mapper Name");
    	
    	UserHistoryDTO userHistoryDTO = new UserHistoryDTO();
    	userHistoryDTO.setUserDTO(userDTO);
    	userHistoryDTO.setId(10000l);
    	UserHistoryEntity userHistoryEntity = userHistorySMapper.toEntity(userHistoryDTO);
    	assertNotNull(userHistoryEntity);
    	assertNotNull(userHistoryEntity.getUsersEntity());
    	assertNotNull(userHistoryEntity.getUsersEntity().getId());
    }
    
    @Test
    void toDTOTest() {
    	
    	UserEntity userEntity = new UserEntity();
    	userEntity.setId(10000l);
    	userEntity.setName("Mapper Name");
    	
    	UserHistoryEntity userHistoryEntity = new UserHistoryEntity();
    	userHistoryEntity.setUsersEntity(userEntity);
    	userHistoryEntity.setId(10000l);
    	UserHistoryDTO userHistoryDTO = userHistorySMapper.toDto(userHistoryEntity);
    	assertNotNull(userHistoryDTO);
    	assertNotNull(userHistoryDTO.getUserDTO());
    	assertNotNull(userHistoryDTO.getUserDTO().getId());
    }
}
