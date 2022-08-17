package ru.tink.practice.exception;

public class PortfolioNotFoundException extends EntityNotFoundException {
    public PortfolioNotFoundException(Long id) {
        super(String.format("Portfolio with id %s not found", id));
    }
}
