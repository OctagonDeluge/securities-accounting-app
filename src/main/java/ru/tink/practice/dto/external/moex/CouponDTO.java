package ru.tink.practice.dto.external.moex;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDTO {
    private LocalDate coupondate;
    @JsonProperty("value_rub")
    private Double value;

    public PaymentDTO toPayment() {
        PaymentDTO payment = new PaymentDTO();
        payment.setPaymentDate(coupondate);
        payment.setValue(value);
        return payment;
    }
}
