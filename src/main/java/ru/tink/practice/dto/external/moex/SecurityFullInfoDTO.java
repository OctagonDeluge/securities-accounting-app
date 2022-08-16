package ru.tink.practice.dto.external.moex;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tink.practice.enumeration.Currency;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityFullInfoDTO {
    private String secid;
    private String name;
    private BigDecimal currentPrice;
    private String group;
    private String exchangeName;
    private Currency currency;
}
