package com.demo.keycloak.model.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.demo.keycloak.model.bean.FeedbackDTO;
import com.demo.keycloak.model.entity.FeedbackEntity;

@Mapper
public interface FeedbackSMapper {

	@Mapping(target = "userDTO", source = "usersEntity")
	FeedbackDTO toDto(FeedbackEntity entity);

	@Mapping(target = "usersEntity", source = "userDTO")
	FeedbackEntity toEntity(FeedbackDTO dto);
}
