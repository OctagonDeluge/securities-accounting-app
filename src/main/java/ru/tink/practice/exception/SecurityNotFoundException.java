package ru.tink.practice.exception;
public class SecurityNotFoundException extends EntityNotFoundException {

    public SecurityNotFoundException(Long id) {
        super(String.format("Security with id %s not found", id));
    }
}
