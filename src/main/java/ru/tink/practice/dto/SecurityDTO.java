package ru.tink.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityDTO {
    private String secid;
    private String shortname;
    private String name;
}
