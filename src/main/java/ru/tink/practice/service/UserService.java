package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.dto.security.SignupRequest;
import ru.tink.practice.entity.User;
import ru.tink.practice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String save(SignupRequest signupRequest) {
        //email validation
        User user = new User(signupRequest.getEmail(), signupRequest.getPassword(), signupRequest.getName());
        userRepository.save(user);
        return "registrated";
    }

}
