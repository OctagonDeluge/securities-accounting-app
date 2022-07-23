package ru.tink.practice.service.external.exchange;

import ru.tink.practice.dto.external.moex.PaymentDTO;
import ru.tink.practice.dto.external.moex.SecurityFullInfoDTO;
import ru.tink.practice.dto.external.moex.SecurityShortInfoDTO;

import java.util.List;

public interface ExternalExchangeService {
    List<SecurityShortInfoDTO> getSecuritiesByName(String securityName);

    List<PaymentDTO> getPaymentsBySecid(String secid);

    SecurityFullInfoDTO getSecurityBySecid(String secid);

    String getServiceName();
}
