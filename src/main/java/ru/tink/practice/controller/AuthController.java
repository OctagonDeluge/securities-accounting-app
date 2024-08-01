package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.TokenRefreshDTO;
import ru.tink.practice.dto.UserDTO;
import ru.tink.practice.dto.request.ChangePasswordRequest;
import ru.tink.practice.entity.RefreshToken;
import ru.tink.practice.entity.Wallet;
import ru.tink.practice.security.SecurityUser;
import ru.tink.practice.security.SigninRequest;
import ru.tink.practice.security.SignupRequest;
import ru.tink.practice.security.jwt.JwtUtils;
import ru.tink.practice.service.RefreshTokenService;
import ru.tink.practice.service.UserService;
import ru.tink.practice.service.WalletService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final WalletService walletService;

    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@Valid @RequestBody SignupRequest signupRequest) {
        return userService.save(signupRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserDTO> signinUser(@RequestBody SigninRequest signinRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
        userService.signinUser(userDetails.getEmail());
        String jwt = jwtUtils.generateJwt(userDetails);
        RefreshToken refreshToken = refreshTokenService.getRefreshToken(userDetails.getId());
        Wallet wallet = walletService.getWalletByClientId(userDetails.getId());
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt)
                .body(new UserDTO(userDetails.getUsername(),
                        userDetails.getName(),
                        refreshToken.getToken(),
                        userDetails.getLevel(),
                        wallet
                        ));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logout(Principal principal) {
        userService.signoutUser(principal.getName());
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtUtils.getCleanJwt())
                .body("You've been signed out!");
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody TokenRefreshDTO request) {
        RefreshToken newRefreshToken = refreshTokenService
                .updateRefreshToken(request.getRefreshToken());
        String newJwt = jwtUtils
                .generateTokenFromUsername(newRefreshToken.getClient().getEmail());
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, newJwt)
                .body(newRefreshToken.getToken());
    }
}
