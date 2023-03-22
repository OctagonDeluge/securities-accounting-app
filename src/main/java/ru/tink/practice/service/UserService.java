package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tink.practice.security.SecurityUser;
import ru.tink.practice.security.SignupRequest;
import ru.tink.practice.entity.User;
import ru.tink.practice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public String save(SignupRequest signupRequest) {
        //email validation
        User user = new User(signupRequest.getEmail(), signupRequest.getPassword(), signupRequest.getName(), true);
        userRepository.save(user);
        return "registrated";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("not found"));
        return new SecurityUser(user);
    }
}
