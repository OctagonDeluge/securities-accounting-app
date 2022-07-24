package ru.tink.practice.exception;

public class SecurityNotFoundException extends RuntimeException {

    public SecurityNotFoundException(Long id) {
        super(String.format("Security with %s not found", id));
    }
}
