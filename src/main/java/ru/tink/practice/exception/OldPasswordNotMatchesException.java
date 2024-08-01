package ru.tink.practice.exception;

public class OldPasswordNotMatchesException extends RuntimeException {
    public OldPasswordNotMatchesException() {
        super("Старый пароль неверный");
    }
}
