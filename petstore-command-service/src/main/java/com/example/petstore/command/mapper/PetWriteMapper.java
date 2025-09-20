package com.example.petstore.command.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.model.PetWrite;

@Mapper
public interface PetWriteMapper {

    @Mapping(target = "status", source = "status")
    @Mapping(target = "photoUrls", source = "photoUrls")
    @Mapping(target = "category", source = "category")
    PetWrite toDto(PetWriteEntity entity);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "photoUrls", source = "photoUrls")
    @Mapping(target = "category", source = "category")
    PetWriteEntity toEntity(PetWrite dto);

    List<PetWrite> toDtoList(List<PetWriteEntity> entityList);

    List<PetWriteEntity> toEntityList(List<PetWrite> dtoList);
}