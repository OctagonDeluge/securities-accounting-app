package ru.tink.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDTO {
    private String username;
    private String name;
    private String refreshToken;
    private boolean authorized;
}
