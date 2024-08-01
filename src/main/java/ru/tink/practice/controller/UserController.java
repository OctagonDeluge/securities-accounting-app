package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.request.ChangePasswordRequest;
import ru.tink.practice.security.SecurityUser;
import ru.tink.practice.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class UserController {

    private final UserService userService;

    @GetMapping
    public void getProfile() {

    }

    @PostMapping("/change")
    public ResponseEntity<String> changePassword(Authentication authentication, @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword((SecurityUser) authentication.getPrincipal(), changePasswordRequest);
        return ResponseEntity.ok("Пароль изменен");
    }
}
