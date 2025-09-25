package com.example.petstore.command.controller;

import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.mapper.PetWriteMapper;
import com.example.petstore.command.model.Category;
import com.example.petstore.command.model.PetWrite;
import com.example.petstore.command.service.PetService;
import com.example.petstore.config.JwtConverter;
import com.example.petstore.common.model.Status;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PetCommandController.class)
@Import(PetCommandControllerTest.NoSecurityConfig.class) // ✅ disable security
class PetCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PetService petService;

    @MockBean
    private PetWriteMapper petWriteMapper;

    @MockBean
    private JwtConverter jwtConverter; // keep mocked for constructor

    /**
     * ✅ Security disabled
     */
    @org.springframework.boot.test.context.TestConfiguration
    static class NoSecurityConfig {
        @Bean
        public org.springframework.security.web.SecurityFilterChain filterChain(
                org.springframework.security.config.annotation.web.builders.HttpSecurity http
        ) throws Exception {
            http.csrf().disable()
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }

    @Test
    void testCreatePet() throws Exception {
        PetWriteEntity entity = new PetWriteEntity();
        entity.setPetId(1L);
        entity.setName("Buddy");
        entity.setStatus(Status.AVAILABLE);
        entity.setPhotoUrl("https://example.com/pet.jpg");
        entity.setDescription("A friendly dog");

        when(petService.createPet(any(PetWriteEntity.class))).thenReturn(entity);

        mockMvc.perform(post("/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(entity)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdatePet() throws Exception {
        PetWrite dto = new PetWrite();
        dto.setPetId(1L);
        dto.setName("Buddy");
        dto.setPhotoUrl("https://example.com/pet.jpg");
        dto.setStatus(Status.SOLD);
        dto.setDescription("Updated description");

        Category category = new Category();
        category.setId(1L);
        category.setName("Dogs");
        dto.setCategory(category);

        PetWriteEntity entity = new PetWriteEntity();
        entity.setPetId(1L);
        entity.setName("Buddy");
        entity.setStatus(Status.SOLD);
        entity.setPhotoUrl("https://example.com/pet.jpg");
        entity.setDescription("Updated description");

        when(petWriteMapper.toEntity(any(PetWrite.class))).thenReturn(entity);
        when(petService.createPet(any(PetWriteEntity.class))).thenReturn(entity);

        mockMvc.perform(put("/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePet() throws Exception {
        doNothing().when(petService).deletePet(1L);

        mockMvc.perform(delete("/pet/1"))
                .andExpect(status().isNoContent()); // 204
    }
}
