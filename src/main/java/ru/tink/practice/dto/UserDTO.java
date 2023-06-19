package ru.tink.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tink.practice.entity.Wallet;
import ru.tink.practice.entity.enumeration.Level;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class UserDTO {
    private String username;
    private String name;
    private String refreshToken;
    private Level level;
    private Wallet wallet;
}
