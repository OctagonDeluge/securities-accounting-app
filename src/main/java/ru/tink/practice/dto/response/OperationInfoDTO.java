package ru.tink.practice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.tink.practice.enumeration.Currency;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class OperationInfoDTO {
    private String secid;
    private Integer quantity;
    private BigDecimal operationCost;
    private BigDecimal overall;
    private Currency currency;
    private BigDecimal walletBalance;
}
