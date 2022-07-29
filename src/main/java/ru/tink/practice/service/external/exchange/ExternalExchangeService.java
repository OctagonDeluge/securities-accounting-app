package ru.tink.practice.service.external.exchange;

import ru.tink.practice.dto.external.moex.*;

import java.util.List;

public interface ExternalExchangeService {
    List<SecurityShortInfoDTO> getSecuritiesByName(String securityName);

    List<PaymentDTO> getPaymentsBySecid(String secid);

    SecurityFullInfoDTO getSecurityBySecid(String secid);

    Double getCurrentSecurityPrice(String secid, String securityType);

    List<CurrentPriceDTO> getPricesForNumberOfDays(Long numberOfDays, String secid, String securityType);

    String getServiceName();
}
