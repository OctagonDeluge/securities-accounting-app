package ru.tink.practice.exception;

public class CandlesForSecurityNotFound extends RuntimeException {
    public CandlesForSecurityNotFound(String secid) {
        super(String.format("Candles for security %s not found",secid));
    }
}
