package ru.tink.practice.service.external.exchange;

import ru.tink.practice.dto.external.moex.*;

import java.util.List;
import java.util.Map;

public interface ExternalExchangeService {
    List<SecurityShortInfoDTO> getSecuritiesByName(String securityName);

    List<PaymentDTO> getPaymentsBySecid(String secid);

    SecurityFullInfoDTO getSecurityBySecid(String secid);

    Double getCurrentSecurityPrice(String secid, String securityType);

    List<CurrentPriceDTO> getPriceStatisticsByNumberOfDays(String secid, Map<String, String> params);

    String getServiceName();
}
