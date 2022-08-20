package ru.tink.practice.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tink.practice.exception.CandlesForSecurityNotFound;
import ru.tink.practice.exception.EntityNotFoundException;
import ru.tink.practice.exception.NoSuchExchangeException;
import ru.tink.practice.exception.SecurityNotFoundInExternalServiceException;
import ru.tink.practice.exception.messages.ExceptionMessage;
import ru.tink.practice.exception.messages.ViolationMessage;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(NoSuchExchangeException.class)
    public ResponseEntity<ExceptionMessage> handleNoSuchExchangeException(NoSuchExchangeException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionMessage> handlePortfolioNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(SecurityNotFoundInExternalServiceException.class)
    public ResponseEntity<ExceptionMessage> handleSecurityNotFoundInExternalServiceException(SecurityNotFoundInExternalServiceException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(CandlesForSecurityNotFound.class)
    public ResponseEntity<ExceptionMessage> handleCandlesForSecurityNotFound(CandlesForSecurityNotFound exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ViolationMessage>> handleConstraintViolationException(ConstraintViolationException exception) {
        List<ViolationMessage> violations = exception.getConstraintViolations().stream()
                .map(violation ->
                        new ViolationMessage(getLastPathElement(violation.getPropertyPath()), violation.getMessage()))
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(violations);
    }

    private String getLastPathElement(Path path) {
        String pathElement = "";
        for (Path.Node node:
             path) {
            pathElement = node.getName();
        }
        return pathElement;
    }
}
