package ru.tink.practice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class SecuritySaleRequest {
    private Long securityId;
    private BigDecimal price;
    private Integer quantity;
    private Long walletId;
}
