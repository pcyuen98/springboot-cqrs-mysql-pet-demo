package com.example.petstore.command.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.petstore.command.entity.PetWriteEntity;
import com.example.petstore.command.mapper.PetWriteMapper;
import com.example.petstore.command.model.PetWrite;
import com.example.petstore.command.service.PetService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/pet")
@Slf4j
public class PetCommandController {
	private final PetService service;
	private final PetWriteMapper petWriteMapper;

	@PostMapping
	public ResponseEntity<PetWriteEntity> createPet(@RequestBody PetWriteEntity pet) {
		PetWriteEntity created = service.createPet(pet);
		return ResponseEntity.ok(created);
	}
	
	@PutMapping
	public ResponseEntity<PetWriteEntity> updatePet(@Valid @RequestBody PetWrite dto) {
		PetWriteEntity pet = petWriteMapper.toEntity(dto);
		PetWriteEntity updated = service.createPet(pet);
		return ResponseEntity.ok(updated);
	}
	
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        service.deletePet(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
