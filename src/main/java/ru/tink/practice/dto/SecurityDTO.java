package ru.tink.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tink.practice.enumeration.Currency;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SecurityDTO {
    @NotBlank
    private String secid;
    @NotBlank
    private String name;
    @Positive
    private Integer quantity;
    @PositiveOrZero
    private BigDecimal purchasePrice;
    @NotBlank
    private String type;
    @NotBlank
    private String exchange;
    @NotNull
    private Currency currency;
    @Positive
    private Long portfolioId;
}
