package com.example.petstore.query.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.petstore.common.events.PetCreatedEvent;
import com.example.petstore.common.exception.DemoAppException;
import com.example.petstore.common.model.Status;
import com.example.petstore.query.model.PetReadEntity;
import com.example.petstore.query.repository.PetReadRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PetEventHandlerTest {

    @Mock
    private PetReadRepository petReadRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PetEventHandler petEventHandler;

    @Captor
    private ArgumentCaptor<PetReadEntity> petCaptor;

    private String badMessage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        badMessage = "not-a-json";
    }

    @Test
    void handlePetCreated_shouldSavePet_whenStatusIsNotDelete() throws Exception {
        // Arrange
        String message = "{\"petId\":1,\"status\":\"AVAILABLE\"}";
        PetCreatedEvent event = new PetCreatedEvent(1l, message, message, message, message, message);
        event.setPetId(1L);
        event.setStatus(Status.AVAILABLE.name());

        when(objectMapper.readValue(message, PetCreatedEvent.class)).thenReturn(event);

        // Act
        petEventHandler.handlePetCreated(message);

        // Assert
        verify(petReadRepository, times(1)).save(petCaptor.capture());
        PetReadEntity savedPet = petCaptor.getValue();
        assert savedPet.getId().equals(1L);
        assert savedPet.getStatus().equals(Status.AVAILABLE.name());
        assert savedPet.getData().equals(message);

        verify(petReadRepository, never()).delete(any());
    }

    @Test
    void handlePetCreated_shouldDeletePet_whenStatusIsDelete() throws Exception {
        // Arrange
        String message = "{\"petId\":2,\"status\":\"DELETE\"}";
        PetCreatedEvent event = new PetCreatedEvent(1l, message, message, message, message, message);
        event.setPetId(2L);
        event.setStatus(Status.DELETE.name());

        when(objectMapper.readValue(message, PetCreatedEvent.class)).thenReturn(event);

        // Act
        petEventHandler.handlePetCreated(message);

        // Assert
        verify(petReadRepository, times(1)).delete(petCaptor.capture());
        PetReadEntity deletedPet = petCaptor.getValue();
        assert deletedPet.getId().equals(2L);
        assert deletedPet.getStatus().equals(Status.DELETE.name());
        assert deletedPet.getData().equals(message);

        verify(petReadRepository, never()).save(any());
    }

    @Test
    void handlePetCreated_shouldThrowDemoAppException_whenJsonIsInvalid() throws Exception {
        // Arrange
        when(objectMapper.readValue(badMessage, PetCreatedEvent.class))
                .thenThrow(new RuntimeException("JSON error"));

        // Act + Assert
        assertThrows(DemoAppException.class, () -> petEventHandler.handlePetCreated(badMessage));

        // Ensure no DB calls are made
        verify(petReadRepository, never()).save(any(PetReadEntity.class));
        verify(petReadRepository, never()).delete(any(PetReadEntity.class));
    }
}
