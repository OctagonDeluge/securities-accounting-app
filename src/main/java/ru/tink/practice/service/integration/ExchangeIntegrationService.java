package ru.tink.practice.service.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.dto.external.moex.CurrentPriceDTO;
import ru.tink.practice.dto.external.moex.PaymentDTO;
import ru.tink.practice.dto.external.moex.SecurityFullInfoDTO;
import ru.tink.practice.dto.external.moex.SecurityShortInfoDTO;
import ru.tink.practice.exception.NoSuchExchangeException;
import ru.tink.practice.service.external.exchange.ExternalExchangeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeIntegrationService {

    private final List<ExternalExchangeService> exchangeServices;

    public List<SecurityShortInfoDTO> loadSecurities(String securityName) {
        List<SecurityShortInfoDTO> securities = new ArrayList<>();
        exchangeServices.forEach(service -> securities.addAll(service.getSecuritiesByName(securityName)));
        return securities;
    }

    public SecurityFullInfoDTO getSecurityBySecid(String secid, String exchangeName) {
        for (ExternalExchangeService service:
             exchangeServices) {
            if(service.getServiceName().equals(exchangeName)) {
                return service.getSecurityBySecid(secid);
            }
        }
        throw new NoSuchExchangeException(exchangeName);
    }

    public List<PaymentDTO> getPaymentsBySecid(String secid, String exchangeName) {
        for (ExternalExchangeService service:
                exchangeServices) {
            if(service.getServiceName().equals(exchangeName)) {
                return service.getPaymentsBySecid(secid);
            }
        }
        throw new NoSuchExchangeException(exchangeName);
    }

    public BigDecimal getCurrentSecurityPrice(String secid, String securityType, String exchangeName) {
        for (ExternalExchangeService service:
                exchangeServices) {
            if(service.getServiceName().equals(exchangeName)) {
                return service.getCurrentSecurityPrice(secid, securityType);
            }
        }
        throw new NoSuchExchangeException(exchangeName);
    }

    public List<CurrentPriceDTO> getPricesForNumberOfDays(String secid, String exchangeName, Map<String, String> params) {
        for (ExternalExchangeService service:
                exchangeServices) {
            if(service.getServiceName().equals(exchangeName)) {
                return service.getPriceStatisticsByNumberOfDays(secid, params);
            }
        }
        throw new NoSuchExchangeException(exchangeName);
    }
}
