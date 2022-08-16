package ru.tink.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tink.practice.enumeration.Currency;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SecurityDTO {
    private String secid;
    private String name;
    private Integer quantity;
    private BigDecimal purchasePrice;
    private String type;
    private String exchange;
    private Currency currency;
    private Long portfolioId;
}
