package ru.tink.practice.exception;

public class PortfolioIsNotEmptyException extends RuntimeException {

    public PortfolioIsNotEmptyException(String portfolioName) {
        super(String.format("%s portfolio is not empty", portfolioName));
    }
}
