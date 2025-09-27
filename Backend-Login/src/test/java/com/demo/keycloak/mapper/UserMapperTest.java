package com.demo.keycloak.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.demo.keycloak.model.bean.UserDTO;
import com.demo.keycloak.model.entity.UserEntity;
import com.demo.keycloak.model.entity.mapper.UserSMapper;

class UserMapperTest {

	private final UserSMapper userSMapper = Mappers.getMapper(UserSMapper.class);
    
    @Test
    void toEntityTest() {

    	
    	UserDTO userDTO = new UserDTO();
    	userDTO.setId(10000l);
    	userDTO.setName("Mapper Name");
    	
    	UserEntity userEntity = userSMapper.toEntity(userDTO);
    	assertNotNull(userEntity);
    	assertNotNull(userEntity.getId());
    }
    
    @Test
    void toDTOTest() {
    	
    	UserEntity userEntity = new UserEntity();
    	userEntity.setId(10000l);
    	userEntity.setName("Mapper Name");

    	UserDTO userDTO = userSMapper.toDto(userEntity);
    	assertNotNull(userDTO);
    	assertNotNull(userDTO.getId());
    }
}
