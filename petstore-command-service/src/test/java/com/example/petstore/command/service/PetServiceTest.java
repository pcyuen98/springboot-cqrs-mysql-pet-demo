package com.example.petstore.command.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import com.example.petstore.command.entity.CategoryEntity;
import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.repository.PetRepository;
import com.example.petstore.common.events.PetCreatedEvent;
import com.example.petstore.common.exception.DemoAppException;
import com.example.petstore.common.model.Status;

class PetServiceTest {

    private PetRepository petRepository;
    private KafkaTemplate<String, PetCreatedEvent> kafkaTemplate;
    private PetService petService;

    @SuppressWarnings("unchecked")
	@BeforeEach
    void setUp() {
        petRepository = mock(PetRepository.class);
        kafkaTemplate = (KafkaTemplate<String, PetCreatedEvent>) mock(KafkaTemplate.class);
        petService = new PetService(petRepository, kafkaTemplate);
    }

    private PetWriteEntity buildPet(Long id) {
        CategoryEntity category = new CategoryEntity();
        category.setName("Dogs");

        PetWriteEntity pet = new PetWriteEntity();
        pet.setPetId(id);
        pet.setName("Buddy");
        pet.setDescription("Friendly dog");
        pet.setPhotoUrl("http://example.com/dog.jpg");
        pet.setStatus(Status.AVAILABLE);
        pet.setCategory(category);
        return pet;
    }

    @Test
    void createPet_ShouldSaveAndPublishEvent() {
        // given
        PetWriteEntity pet = buildPet(null);
        PetWriteEntity savedPet = buildPet(1L);
        when(petRepository.save(pet)).thenReturn(savedPet);

        // when
        PetWriteEntity result = petService.createPet(pet);

        // then
        assertThat(result.getPetId()).isEqualTo(1L);
        verify(petRepository, times(1)).save(pet);

        ArgumentCaptor<PetCreatedEvent> eventCaptor = ArgumentCaptor.forClass(PetCreatedEvent.class);
        verify(kafkaTemplate, times(1)).send(eq("pets"), eventCaptor.capture());

        PetCreatedEvent event = eventCaptor.getValue();
        assertThat(event.getPetId()).isEqualTo(1L);
        assertThat(event.getName()).isEqualTo("Buddy");
        assertThat(event.getStatus()).isEqualTo("AVAILABLE");
    }

    @Test
    void deletePet_ShouldPublishDeleteEventAndRemoveFromRepository() {
        // given
        PetWriteEntity pet = buildPet(1L);
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        // when
        petService.deletePet(1L);

        // then
        verify(petRepository, times(1)).findById(1L);
        verify(petRepository, times(1)).deleteById(1L);

        ArgumentCaptor<PetCreatedEvent> eventCaptor = ArgumentCaptor.forClass(PetCreatedEvent.class);
        verify(kafkaTemplate, times(1)).send(eq("pets"), eventCaptor.capture());

        PetCreatedEvent event = eventCaptor.getValue();
        assertThat(event.getPetId()).isEqualTo(1L);
        assertThat(event.getStatus()).isEqualTo("DELETE");
    }

    @Test
    void deletePet_ShouldThrowException_WhenPetNotFound() {
        // given
        when(petRepository.findById(99L)).thenReturn(Optional.empty());

        // when + then
        assertThrows(DemoAppException.class, () -> petService.deletePet(99L));

        verify(petRepository, times(1)).findById(99L);
        verifyNoInteractions(kafkaTemplate);
        verify(petRepository, never()).deleteById(anyLong());
    }
}
