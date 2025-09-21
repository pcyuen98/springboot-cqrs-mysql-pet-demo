package com.demo.keycloak.model.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.demo.keycloak.model.bean.UserHistoryDTO;
import com.demo.keycloak.model.entity.UserHistoryEntity;

@Mapper
public interface UserHistorySMapper {

	@Mapping(target = "userDTO", source = "usersEntity")
	UserHistoryDTO toDto(UserHistoryEntity entity);

	@Mapping(target = "usersEntity", source = "userDTO")
	UserHistoryEntity toEntity(UserHistoryDTO dto);

}