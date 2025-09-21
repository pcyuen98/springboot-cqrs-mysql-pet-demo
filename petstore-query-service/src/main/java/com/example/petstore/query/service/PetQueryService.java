package com.example.petstore.query.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.petstore.query.model.PetReadEntity;
import com.example.petstore.query.repository.PetReadRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetQueryService {

    private final PetReadRepository petReadRepository;

    public List<PetReadEntity> findAllPets() {
        return petReadRepository.findAll();
    }
}
