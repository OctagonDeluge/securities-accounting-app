package ru.tink.practice.dto.external.moex;

import ru.tink.practice.enumeration.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Payment {
    LocalDate getPaymentDate();

    BigDecimal getPaymentCost();

    Currency getPaymentCurrency();
}
