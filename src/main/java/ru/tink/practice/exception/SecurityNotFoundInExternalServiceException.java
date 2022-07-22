package ru.tink.practice.exception;

public class SecurityNotFoundInExternalServiceException extends RuntimeException {
    public SecurityNotFoundInExternalServiceException(String secid, String exchangeName) {
        super(String.format("Security with %s not found in external service %s", secid, exchangeName));
    }
}
