package com.example.petstore.query.service;

import com.example.petstore.common.model.Status;
import com.example.petstore.query.model.PetReadEntity;
import com.example.petstore.query.repository.PetReadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetQueryServiceTest {

    @Mock
    private PetReadRepository petReadRepository;

    @InjectMocks
    private PetQueryService petQueryService;

    private PetReadEntity pet1;
    private PetReadEntity pet2;

    @BeforeEach
    void setUp() {
        pet1 = new PetReadEntity();
        pet1.setId(1L);
        pet1.setData("Dog");
        pet1.setStatus(Status.AVAILABLE.name()); // ✅ use enum

        pet2 = new PetReadEntity();
        pet2.setId(2L);
        pet2.setData("Cat");
        pet2.setStatus(Status.SOLD.name()); // ✅ use enum
    }

    @Test
    void testFindAllPets() {
        when(petReadRepository.findAll()).thenReturn(Arrays.asList(pet1, pet2));

        List<PetReadEntity> result = petQueryService.findAllPets();

        assertThat(result).hasSize(2).containsExactly(pet1, pet2);
        verify(petReadRepository, times(1)).findAll();
    }

    @Test
    void testFindPetById() {
        when(petReadRepository.findById(1L)).thenReturn(Optional.of(pet1));

        Optional<PetReadEntity> result = petQueryService.findPetById(1L);

        assertThat(result).isPresent().contains(pet1);
        verify(petReadRepository, times(1)).findById(1L);
    }

    @Test
    void testFindPetsByStatus() {
        when(petReadRepository.findByStatus(Status.AVAILABLE.name())).thenReturn(List.of(pet1));

        List<PetReadEntity> result = petQueryService.findPetsByStatus(Status.AVAILABLE.name());

        assertThat(result).containsExactly(pet1);
        verify(petReadRepository, times(1)).findByStatus(Status.AVAILABLE.name());
    }

    @Test
    void testFindPetsByStatusAndData_withValidValues() {
        when(petReadRepository.search(Status.AVAILABLE.name(), "Dog")).thenReturn(List.of(pet1));

        List<PetReadEntity> result = petQueryService.findPetsByStatusAndData(Status.AVAILABLE.name(), "Dog");

        assertThat(result).containsExactly(pet1);
        verify(petReadRepository, times(1)).search(Status.AVAILABLE.name(), "Dog");
    }

    @Test
    void testFindPetsByStatusAndData_withUndefinedValues() {
        when(petReadRepository.search(".*", ".*")).thenReturn(Arrays.asList(pet1, pet2));

        List<PetReadEntity> result = petQueryService.findPetsByStatusAndData("undefined", "undefined");

        assertThat(result).containsExactly(pet1, pet2);
        verify(petReadRepository, times(1)).search(".*", ".*");
    }

    @Test
    void testFindPetsByStatusAndData_withNullStatus() {
        when(petReadRepository.search(".*", "Dog")).thenReturn(List.of(pet1));

        List<PetReadEntity> result = petQueryService.findPetsByStatusAndData(null, "Dog");

        assertThat(result).containsExactly(pet1);
        verify(petReadRepository, times(1)).search(".*", "Dog");
    }

    @Test
    void testFindPetsByStatusAndData_withNullData() {
        when(petReadRepository.search(Status.AVAILABLE.name(), ".*")).thenReturn(List.of(pet1));

        List<PetReadEntity> result = petQueryService.findPetsByStatusAndData(Status.AVAILABLE.name(), null);

        assertThat(result).containsExactly(pet1);
        verify(petReadRepository, times(1)).search(Status.AVAILABLE.name(), ".*");
    }

    @Test
    void testFindPetsByStatusAndData_withEmptyStatusAndData() {
        when(petReadRepository.search(".*", ".*")).thenReturn(Arrays.asList(pet1, pet2));

        List<PetReadEntity> result = petQueryService.findPetsByStatusAndData("", "");

        assertThat(result).containsExactly(pet1, pet2);
        verify(petReadRepository, times(1)).search(".*", ".*");
    }

    @Test
    void testFindPetsByStatusAndData_withBlankStatus() {
        when(petReadRepository.search(".*", "Dog")).thenReturn(List.of(pet1));

        List<PetReadEntity> result = petQueryService.findPetsByStatusAndData("   ", "Dog");

        assertThat(result).containsExactly(pet1);
        verify(petReadRepository, times(1)).search(".*", "Dog");
    }
    
    @Test
    void testWithEmptyStatusAndData() {
        when(petReadRepository.search(".*", ".*"))
                .thenReturn(Arrays.asList(pet1, pet2));

        List<PetReadEntity> result =
                petQueryService.findPetsByStatusAndDataByWildCard("", "");

        assertThat(result).containsExactly(pet1, pet2);
        verify(petReadRepository).search(".*", ".*");
    }

    @Test
    void testWithUndefinedValues() {
        when(petReadRepository.search("undefined", "undefined"))
                .thenReturn(Arrays.asList(pet1, pet2));

        List<PetReadEntity> result =
                petQueryService.findPetsByStatusAndDataByWildCard("undefined", "undefined");

        assertThat(result).containsExactly(pet1, pet2);
        verify(petReadRepository).search("undefined", "undefined");
    }
}
