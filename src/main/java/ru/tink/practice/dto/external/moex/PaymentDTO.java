package ru.tink.practice.dto.external.moex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tink.practice.enumeration.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private LocalDate paymentDate;
    private BigDecimal cost;
    private Currency currency;
}
