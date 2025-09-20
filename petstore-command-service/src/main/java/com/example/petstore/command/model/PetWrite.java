package com.example.petstore.command.model;

import java.util.List;

import com.example.petstore.common.model.Status;

import lombok.Data;

@Data
public class PetWrite {

    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private Status status;
}
