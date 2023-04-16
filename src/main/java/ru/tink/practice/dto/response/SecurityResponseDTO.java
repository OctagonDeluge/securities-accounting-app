package ru.tink.practice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tink.practice.entity.Security;
import ru.tink.practice.enumeration.Currency;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityResponseDTO {
    private Long id;
    private String secid;
    private String name;
    private BigDecimal totalCost;
    private BigDecimal profit;
    private String group;
    private String exchangeName;
    private Currency currency;

    public SecurityResponseDTO(Security security) {
        id = security.getId();
        secid = security.getSecid();
        name = security.getName();
        totalCost = security.getTotalCost();
        profit = security.getProfit();
        group = security.getType().getName();
        exchangeName = security.getExchange().getName();
        currency = security.getCurrency();
    }
}
