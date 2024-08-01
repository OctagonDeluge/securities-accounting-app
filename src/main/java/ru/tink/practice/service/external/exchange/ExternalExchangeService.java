package ru.tink.practice.service.external.exchange;

import ru.tink.practice.dto.external.moex.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ExternalExchangeService {
    List<SecurityShortInfoDTO> getSecuritiesByName(String securityName);

    SecurityFullInfoDTO getSecurityBySecid(String secid);

    BigDecimal getCurrentSecurityPrice(String secid, String securityType);

    List<CurrentPriceDTO> getPriceStatisticsByNumberOfDays(String secid, Map<String, String> params);

    List<PaymentDTO> getPaymentsBySecid(String secid);

    List<DividendDTO> getDividendsInfo(String secid);

    CouponsDTO getCouponsInfo(String secid);
}
