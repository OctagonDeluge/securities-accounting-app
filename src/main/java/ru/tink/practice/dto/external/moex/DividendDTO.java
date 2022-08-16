package ru.tink.practice.dto.external.moex;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tink.practice.enumeration.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DividendDTO {
    @JsonProperty("registryclosedate")
    private LocalDate dividendDate;
    @JsonProperty("value")
    private BigDecimal cost;
    @JsonProperty("currencyid")
    private Currency currency;

    public PaymentDTO toPayment() {
        PaymentDTO payment = new PaymentDTO();
        payment.setPaymentDate(dividendDate);
        payment.setCost(cost);
        payment.setCurrency(currency);
        return payment;
    }
}
