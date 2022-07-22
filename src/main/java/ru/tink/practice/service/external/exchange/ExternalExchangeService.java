package ru.tink.practice.service.external.exchange;

import ru.tink.practice.dto.external.moex.PaymentDTO;
import ru.tink.practice.dto.external.moex.SecurityDTO;

import java.util.List;

public interface ExternalExchangeService {
    List<SecurityDTO> getSecuritiesByName(String securityName);

    List<PaymentDTO> getPaymentsBySecid(String secid);

    SecurityDTO getSecurityBySecid(String secid);

    String getServiceName();
}
