package ru.tink.practice.dto.external.moex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tink.practice.enumeration.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmortizationDTO implements Payment {
    @JsonProperty("amortdate")
    LocalDate amortizationDate;
    @JsonProperty("value_rub")
    BigDecimal cost;
    @JsonProperty("faceunit")
    private Currency currency;

    @Override
    public LocalDate getPaymentDate() {
        return amortizationDate;
    }

    @Override
    public BigDecimal getPaymentCost() {
        return cost;
    }

    @Override
    public Currency getPaymentCurrency() {
        return currency;
    }
}
