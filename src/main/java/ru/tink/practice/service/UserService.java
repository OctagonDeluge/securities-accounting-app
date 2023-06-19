package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.tink.practice.dto.request.ChangePasswordRequest;
import ru.tink.practice.entity.User;
import ru.tink.practice.entity.Wallet;
import ru.tink.practice.entity.enumeration.Role;
import ru.tink.practice.enumeration.Currency;
import ru.tink.practice.exception.OldPasswordNotMatchesException;
import ru.tink.practice.repository.UserRepository;
import ru.tink.practice.repository.WalletRepository;
import ru.tink.practice.security.SecurityUser;
import ru.tink.practice.security.SignupRequest;

import javax.naming.AuthenticationException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final boolean AUTHENTICATED = true;
    private final boolean NOT_AUTHENTICATED = false;

    public ResponseEntity<String> save(SignupRequest signupRequest) {
        //:TODO email validation
        if (userRepository.findByEmail(signupRequest.getEmail()).isEmpty()) {
            User user = new User(signupRequest.getEmail(),
                    bCryptPasswordEncoder.encode(signupRequest.getPassword()),
                    signupRequest.getName(),
                    signupRequest.getLevel(),
                    Role.USER,
                    true);
            Wallet wallet = new Wallet("Кошелек",
                    BigDecimal.valueOf(signupRequest.getLevel().getMoney()),
                    Currency.RUB,
                    user
            );
            userRepository.save(user);
            walletRepository.save(wallet);
            return ResponseEntity.ok("User successfully registered");
        } else {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "User with such email already exists");
        }
    }

    public User findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void changePassword(SecurityUser securityUser, ChangePasswordRequest changePasswordRequest) {
        if (bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword() ,securityUser.getPassword())) {
            userRepository.updatePassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()), securityUser.getEmail());
        } else {
            throw new OldPasswordNotMatchesException();
        }
    }

    public void signinUser(String email) {
        userRepository.updateAuthByEmail(AUTHENTICATED, email);
    }

    public void signoutUser(String email) {
        userRepository.updateAuthByEmail(NOT_AUTHENTICATED, email);
    }
}
