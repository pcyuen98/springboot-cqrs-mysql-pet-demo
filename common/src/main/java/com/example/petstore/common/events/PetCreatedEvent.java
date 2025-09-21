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

    public PetCreatedEvent() {}

    public Long getPetId() { return petId; }
    public void setPetId(Long petId) { this.petId = petId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
