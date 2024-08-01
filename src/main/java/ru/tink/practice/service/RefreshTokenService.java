package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tink.practice.entity.RefreshToken;
import ru.tink.practice.entity.User;
import ru.tink.practice.exception.RefreshTokenExpiredException;
import ru.tink.practice.exception.RefreshTokenNotFoundException;
import ru.tink.practice.repository.RefreshTokenRepository;
import ru.tink.practice.repository.UserRepository;

import java.sql.Ref;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    @Transactional
    public RefreshToken updateRefreshToken(String oldRefreshToken) {
        RefreshToken refreshToken = findByToken(oldRefreshToken);
        validateRefreshToken(refreshToken);
        return updateRefreshToken(refreshToken.getClient(), refreshToken);
    }

    public RefreshToken getRefreshToken(Long userId) {
        RefreshToken refreshToken =  refreshTokenRepository.findByClientId(userId).orElse(null);
        if(refreshToken == null || (refreshToken.getExpirationTime() < new Date().getTime())) {
            refreshToken = createRefreshToken(userId);
        }

        return refreshToken;
    }

    private RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(RefreshTokenNotFoundException::new);
    }

    private RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                new Date().getTime() + expirationDays*24L*60L*60L*1000L,
                userRepository.findById(userId).orElseThrow(() ->
                        new UsernameNotFoundException("User not found"))
        );
        return refreshTokenRepository.save(refreshToken);
    }

    private RefreshToken updateRefreshToken(User user, RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
        RefreshToken token = new RefreshToken(
                UUID.randomUUID().toString(),
                new Date().getTime() + expirationDays*24L*60L*60L*1000L,
                user
        );
        return refreshTokenRepository.save(token);
    }

    private void validateRefreshToken(RefreshToken token) {
        if(new Date().getTime() > token.getExpirationTime()) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpiredException(token.getToken());
        }
    }
}
