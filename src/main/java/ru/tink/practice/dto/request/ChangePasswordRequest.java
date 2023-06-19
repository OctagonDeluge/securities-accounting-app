package ru.tink.practice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class ChangePasswordRequest {
    private String oldPassword;
    @Size(min=6, max=100,
            message = "Password must be more than 6 characters")
    @NotEmpty
    private String newPassword;
}
