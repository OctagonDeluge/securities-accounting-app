package ru.tink.practice.dto.external.moex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityShortInfoDTO {
    private String secid;
    private String shortname;
    private String group;
    private String exchangeName;
}
