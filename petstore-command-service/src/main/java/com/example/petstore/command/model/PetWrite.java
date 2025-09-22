package com.example.petstore.command.model;

import java.util.List;

import com.example.petstore.common.model.Status;
import com.example.petstore.command.validation.ValidPhotoUrl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PetWrite {

    private Long petId;

    @NotNull(message = "Category is required")
    private Category category;
    
    @NotNull(message = "Description is required")
    private String description;

    @NotBlank(message = "Name is required")
    private String name;

    @ValidPhotoUrl
    private String photoUrl;

    private List<Tag> tags;

    @NotNull(message = "Status is required")
    private Status status;
}
