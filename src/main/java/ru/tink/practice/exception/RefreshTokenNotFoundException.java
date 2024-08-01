package ru.tink.practice.exception;

public class RefreshTokenNotFoundException extends EntityNotFoundException {
    public RefreshTokenNotFoundException() {
        super("Token not found");
    }
}
