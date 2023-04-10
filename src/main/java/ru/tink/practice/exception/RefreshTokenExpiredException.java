package ru.tink.practice.exception;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException(String token) {
        super(String.format("%s refresh token was expired, please make a new signin request", token));
    }
}
