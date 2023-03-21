package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tink.practice.dto.security.SigninRequest;
import ru.tink.practice.dto.security.SignupRequest;
import ru.tink.practice.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signupUser(@RequestBody SignupRequest signupRequest) {
        return userService.save(signupRequest);
    }

    @PostMapping("/signin")
    public String signinUser(@RequestBody SigninRequest signinRequest) {
        return "stub";
    }
}
