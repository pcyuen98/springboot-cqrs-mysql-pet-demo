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

    @JsonCreator
    public PetCreatedEvent(@JsonProperty("petId") Long petId,
                           @JsonProperty("name") String name,
                           @JsonProperty("type") String type,
                           @JsonProperty("status") String status) {
        this.petId = petId;
        this.name = name;
        this.type = type;
        this.status = status;
    }

}
