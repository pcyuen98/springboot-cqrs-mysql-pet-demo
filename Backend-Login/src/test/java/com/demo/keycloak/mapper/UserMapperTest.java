package com.demo.keycloak.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.keycloak.model.bean.UserDTO;
import com.demo.keycloak.model.entity.UserEntity;
import com.demo.keycloak.model.entity.mapper.UserSMapper;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserSMapper userSMapper;
    
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
