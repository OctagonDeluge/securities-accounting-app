package ru.tink.practice.exception;

public class SecurityNotFoundException extends RuntimeException {

    public SecurityNotFoundException(Integer id) {
        super(String.format("Security with %s not found", id));
    }
}
