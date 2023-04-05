package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.tink.practice.entity.Role;
import ru.tink.practice.entity.User;
import ru.tink.practice.repository.UserRepository;
import ru.tink.practice.security.SignupRequest;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    public ResponseEntity<String> save(SignupRequest signupRequest) {
        //email validation
        if(userRepository.findByEmail(signupRequest.getEmail()).isEmpty()) {
            User user = new User(signupRequest.getEmail(),
                    bCryptPasswordEncoder.encode(signupRequest.getPassword()),
                    signupRequest.getName(),
                    Role.USER,
                    true);
            userRepository.save(user);
            return ResponseEntity.ok("User successfully registered");
        } else {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User with such email already exists");
        }
    }
}
