package com.example.petstore.common.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PetCreatedEvent {
    private Long petId;
    private String name;
    private String type;
    private String status;
    private String photoUrl;
    private String description;

    @JsonCreator
    public PetCreatedEvent(
            @JsonProperty("petId") Long petId,
            @JsonProperty("name") String name,
            @JsonProperty("type") String type,
            @JsonProperty("status") String status,
            @JsonProperty("photoUrl") String photoUrl,
            @JsonProperty("description") String description) {
        this.petId = petId;
        this.name = name;
        this.type = type;
        this.status = status;
        this.photoUrl = photoUrl;
        this.description = description;
        
    }

}
