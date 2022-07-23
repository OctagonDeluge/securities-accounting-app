package ru.tink.practice.exception;

public class SecurityNotFoundInExternalServiceException extends RuntimeException {

    public SecurityNotFoundInExternalServiceException(String secid) {
        super(String.format("Security with %s not found in external service", secid));
    }
}
