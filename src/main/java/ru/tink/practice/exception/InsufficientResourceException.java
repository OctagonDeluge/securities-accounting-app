package ru.tink.practice.exception;

public class InsufficientResourceException extends RuntimeException {
    public InsufficientResourceException(String resourceType) {
        super(String.format("Insufficient %s to perform operation", resourceType));
    }
}
