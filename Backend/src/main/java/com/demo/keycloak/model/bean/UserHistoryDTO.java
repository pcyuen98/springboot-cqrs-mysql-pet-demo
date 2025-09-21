package com.demo.keycloak.model.bean;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHistoryDTO {

    private Long id;
    private LocalDateTime loginDate;

    private UserDTO userDTO;
}
