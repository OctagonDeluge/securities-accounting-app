package ru.tink.practice.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.tink.practice.entity.enumeration.Level;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {
    private String name;
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Not valid format for email address")
    @NotEmpty
    private String email;
    private Level level;
    @Size(min=6, max=100,
            message = "Password must be more than 6 characters")
    @NotEmpty
    private String password;
}
