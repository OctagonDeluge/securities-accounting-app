package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tink.practice.entity.RefreshToken;
import ru.tink.practice.entity.User;
import ru.tink.practice.exception.RefreshTokenExpiredException;
import ru.tink.practice.exception.RefreshTokenNotFoundException;
import ru.tink.practice.repository.RefreshTokenRepository;
import ru.tink.practice.repository.UserRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${refreshToken.expirationDays}")
    private Long expirationDays;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(RefreshTokenNotFoundException::new);
    }

    public Optional<RefreshToken> findByUserId(Long userId) {
        return refreshTokenRepository.findByClientId(userId);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                new Date().getTime() + expirationDays*24L*60L*60L*1000L,
                userRepository.findById(userId).orElseThrow(() ->
                        new UsernameNotFoundException("User not found"))
        );
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken updateRefreshToken(User user, RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
        RefreshToken token = new RefreshToken(
                UUID.randomUUID().toString(),
                new Date().getTime() + expirationDays*24L*60L*60L*1000L,
                user
        );
        return refreshTokenRepository.save(token);
    }

    public RefreshToken validateRefreshToken(RefreshToken token) {
        if(new Date().getTime() > token.getExpirationTime()) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpiredException(token.getToken());
        }

        return token;
    }
}
