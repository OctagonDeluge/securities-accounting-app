package ru.tink.practice.dto.external.moex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DividendDTO {
    private LocalDate registryclosedate;
    private Double value;

    public PaymentDTO toPayment() {
        PaymentDTO payment = new PaymentDTO();
        payment.setPaymentDate(registryclosedate);
        payment.setValue(value);
        return payment;
    }
}
