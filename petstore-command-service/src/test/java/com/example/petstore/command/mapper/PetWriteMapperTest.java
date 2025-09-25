package com.example.petstore.command.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.petstore.command.entity.CategoryEntity;
import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.model.PetWrite;
import com.example.petstore.common.model.Status;

@SpringBootTest
@ActiveProfiles("test") // Uses application-test.properties for H2 setup
class PetWriteMapperTest {

	@Autowired
    private PetWriteMapper mapper;

    private PetWriteEntity sampleEntity;
    private PetWrite sampleDto;

    @BeforeEach
    void setUp() {
        // CategoryEntity
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Dogs");

        // Entity
        sampleEntity = new PetWriteEntity();
        sampleEntity.setPetId(10L);
        sampleEntity.setDescription("A very friendly dog");
        sampleEntity.setCategory(categoryEntity);
        sampleEntity.setName("Buddy");
        sampleEntity.setStatus(Status.AVAILABLE);

        // DTO
        com.example.petstore.command.model.Category categoryDto =
                new com.example.petstore.command.model.Category();
        categoryDto.setId(1L);
        categoryDto.setName("Dogs");

        sampleDto = new PetWrite();
        sampleDto.setCategory(categoryDto);
        sampleDto.setName("Max");
        sampleDto.setStatus(Status.PENDING);

        // Extra Entity for list test
        PetWriteEntity entity2 = new PetWriteEntity();
        entity2.setPetId(1l);
        entity2.setName("Molly");
        entity2.setCategory(categoryEntity);
        entity2.setStatus(Status.SOLD);

        // Extra DTO for list test
        com.example.petstore.command.model.Category categoryDto2 =
                new com.example.petstore.command.model.Category();
        categoryDto2.setId(1L);
        categoryDto2.setName("Dogs");

        PetWrite dto2 = new PetWrite();
        dto2.setName("Luna");
        dto2.setCategory(categoryDto2);
        dto2.setStatus(Status.AVAILABLE);

    }

    @Test
    void testToDto() {
        PetWrite dto = mapper.toDto(sampleEntity);
        assertNotNull(dto);
    }

}
