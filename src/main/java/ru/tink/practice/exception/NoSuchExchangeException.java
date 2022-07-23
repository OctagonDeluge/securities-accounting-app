package ru.tink.practice.exception;

public class NoSuchExchangeException extends RuntimeException {

    public NoSuchExchangeException(String exchangeName) {
        super(String.format("Exchange with name %s not found", exchangeName));
    }
}
