package com.demo.keycloak.model.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private String username;
    private String name;
    private String surname;
    private String photo;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime deletionDate;
    private LocalDateTime lastUpdatedDate;
}