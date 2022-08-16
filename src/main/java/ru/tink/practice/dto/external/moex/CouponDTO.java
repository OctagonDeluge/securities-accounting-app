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
public class CouponDTO {
    @JsonProperty("coupondate")
    private LocalDate couponDate;
    @JsonProperty("value_rub")
    private BigDecimal cost;
    @JsonProperty("faceunit")
    private Currency currency;

    public PaymentDTO toPayment() {
        PaymentDTO payment = new PaymentDTO();
        payment.setPaymentDate(couponDate);
        payment.setCost(cost);
        payment.setCurrency(currency);
        return payment;
    }
}
