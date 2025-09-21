package com.demo.keycloak.model.entity.mapper;

import org.mapstruct.Mapper;

import com.demo.keycloak.model.bean.UserDTO;
import com.demo.keycloak.model.entity.UserEntity;

@Mapper
public interface UserSMapper {

	UserDTO toDto(UserEntity entity);

	UserEntity toEntity(UserDTO dto);

}
