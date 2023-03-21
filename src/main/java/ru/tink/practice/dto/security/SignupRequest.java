package ru.tink.practice.dto.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {
    private String name;
    private String email;
    private String password;
}
