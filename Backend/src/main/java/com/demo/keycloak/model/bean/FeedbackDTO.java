package com.demo.keycloak.model.bean;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FeedbackDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private Integer feedbackType;
    private String msg;
    private String answer;
    private LocalDateTime creationDate;
    private UserDTO userDTO;
}
