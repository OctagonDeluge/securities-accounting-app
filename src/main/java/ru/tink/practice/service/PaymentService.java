package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.entity.Payment;
import ru.tink.practice.repository.PaymentRepository;
import ru.tink.practice.service.integration.ExchangeIntegrationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ExchangeIntegrationService exchangeIntegrationService;

    public List<Payment> getPayments(String secid) {
        return null;
    }
}
