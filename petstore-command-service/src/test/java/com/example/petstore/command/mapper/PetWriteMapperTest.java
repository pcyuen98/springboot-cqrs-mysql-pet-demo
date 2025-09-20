package com.example.petstore.command.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.petstore.command.entity.CategoryEntity;
import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.model.PetWrite;
import com.example.petstore.common.model.Status;

@SpringBootTest
class PetWriteMapperTest {

	@Autowired
    private PetWriteMapper mapper;

    private PetWriteEntity sampleEntity;
    private PetWrite sampleDto;
    private List<PetWriteEntity> sampleEntityList;
    private List<PetWrite> sampleDtoList;

    @BeforeEach
    void setUp() {
        // CategoryEntity
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Dogs");

        // Entity
        sampleEntity = new PetWriteEntity();
        sampleEntity.setId(10L);
        sampleEntity.setCategory(categoryEntity);
        sampleEntity.setName("Buddy");
        sampleEntity.setPhotoUrls(Arrays.asList("photo1.jpg", "photo2.jpg"));
        sampleEntity.setStatus(Status.AVAILABLE);

        // DTO
        com.example.petstore.command.model.Category categoryDto =
                new com.example.petstore.command.model.Category();
        categoryDto.setId(1L);
        categoryDto.setName("Dogs");

        sampleDto = new PetWrite();
        sampleDto.setId(20L);
        sampleDto.setCategory(categoryDto);
        sampleDto.setName("Max");
        sampleDto.setPhotoUrls(Arrays.asList("photo3.jpg", "photo4.jpg"));
        sampleDto.setStatus(Status.PENDING);

        // Extra Entity for list test
        PetWriteEntity entity2 = new PetWriteEntity();
        entity2.setId(11L);
        entity2.setName("Molly");
        entity2.setCategory(categoryEntity);
        entity2.setPhotoUrls(Arrays.asList("photo5.jpg"));
        entity2.setStatus(Status.SOLD);

        // Extra DTO for list test
        com.example.petstore.command.model.Category categoryDto2 =
                new com.example.petstore.command.model.Category();
        categoryDto2.setId(1L);
        categoryDto2.setName("Dogs");

        PetWrite dto2 = new PetWrite();
        dto2.setId(21L);
        dto2.setName("Luna");
        dto2.setCategory(categoryDto2);
        dto2.setPhotoUrls(Arrays.asList("photo6.jpg"));
        dto2.setStatus(Status.AVAILABLE);

        sampleEntityList = Arrays.asList(sampleEntity, entity2);
        sampleDtoList = Arrays.asList(sampleDto, dto2);
    }

    @Test
    void testToDto() {
        PetWrite dto = mapper.toDto(sampleEntity);
        assertNotNull(dto);
        assertEquals(sampleEntity.getId(), dto.getId());
        assertEquals(sampleEntity.getName(), dto.getName());
        assertEquals(sampleEntity.getCategory().getId(), dto.getCategory().getId());
        assertEquals(sampleEntity.getPhotoUrls(), dto.getPhotoUrls());
        assertEquals(sampleEntity.getStatus(), dto.getStatus());
    }

    @Test
    void testToEntity() {
        PetWriteEntity entity = mapper.toEntity(sampleDto);
        assertNotNull(entity);
        assertEquals(sampleDto.getId(), entity.getId());
        assertEquals(sampleDto.getName(), entity.getName());
        assertEquals(sampleDto.getCategory().getId(), entity.getCategory().getId());
        assertEquals(sampleDto.getPhotoUrls(), entity.getPhotoUrls());
        assertEquals(sampleDto.getStatus(), entity.getStatus());
    }

    @Test
    void testToDtoList() {
        List<PetWrite> dtoList = mapper.toDtoList(sampleEntityList);
        assertNotNull(dtoList);
        assertEquals(sampleEntityList.size(), dtoList.size());

        assertEquals(sampleEntity.getId(), dtoList.get(0).getId());
        assertEquals(sampleEntity.getName(), dtoList.get(0).getName());

        assertEquals(sampleEntityList.get(1).getId(), dtoList.get(1).getId());
        assertEquals(sampleEntityList.get(1).getName(), dtoList.get(1).getName());
    }

    @Test
    void testToEntityList() {
        List<PetWriteEntity> entityList = mapper.toEntityList(sampleDtoList);
        assertNotNull(entityList);
        assertEquals(sampleDtoList.size(), entityList.size());

        assertEquals(sampleDto.getId(), entityList.get(0).getId());
        assertEquals(sampleDto.getName(), entityList.get(0).getName());

        assertEquals(sampleDtoList.get(1).getId(), entityList.get(1).getId());
        assertEquals(sampleDtoList.get(1).getName(), entityList.get(1).getName());
    }
}
