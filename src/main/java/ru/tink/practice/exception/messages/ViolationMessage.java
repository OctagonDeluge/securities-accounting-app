package ru.tink.practice.exception.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViolationMessage {
    private String fieldName;
    private String message;
}
