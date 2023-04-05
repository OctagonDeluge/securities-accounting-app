package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tink.practice.security.SigninRequest;
import ru.tink.practice.security.SignupRequest;
import ru.tink.practice.security.SecurityUserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SecurityUserService securityUserService;

    @PostMapping("/signup")
    public String signupUser(@RequestBody SignupRequest signupRequest) {
        return securityUserService.save(signupRequest);
    }

    @PostMapping("/signin")
    public String signinUser(@RequestBody SigninRequest signinRequest) {
        return "stub";
    }
}
