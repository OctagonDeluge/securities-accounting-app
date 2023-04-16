package ru.tink.practice.service.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.tink.practice.dto.external.moex.CurrentPriceDTO;
import ru.tink.practice.dto.external.moex.PaymentDTO;
import ru.tink.practice.dto.external.moex.SecurityFullInfoDTO;
import ru.tink.practice.dto.external.moex.SecurityShortInfoDTO;
import ru.tink.practice.exception.NoSuchExchangeException;
import ru.tink.practice.service.external.exchange.ExternalExchangeService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeIntegrationService {
    private final ApplicationContext context;

    /*public List<SecurityShortInfoDTO> loadSecuritiesByName(String securityName) {
        List<SecurityShortInfoDTO> securities = new ArrayList<>();
        exchangeServices.forEach(service -> securities.addAll(service.getSecuritiesByName(securityName)));
        return securities;
    }*/

    public List<SecurityShortInfoDTO> loadSecuritiesByExchange(String securityName, String exchangeName) {
        return determineExchange(exchangeName).getSecuritiesByName(securityName);
    }

    public SecurityFullInfoDTO getSecurityBySecid(String secid, String exchangeName) {
        return determineExchange(exchangeName).getSecurityBySecid(secid);
    }

    public List<PaymentDTO> getPaymentsBySecid(String secid, String exchangeName) {
        return determineExchange(exchangeName).getPaymentsBySecid(secid);
    }

    public BigDecimal getCurrentSecurityPrice(String secid, String securityType, String exchangeName) {
        return determineExchange(exchangeName).getCurrentSecurityPrice(secid, securityType);
    }

    public List<CurrentPriceDTO> getPricesForNumberOfDays(String secid, String exchangeName, Map<String, String> params) {
        return determineExchange(exchangeName).getPriceStatisticsByNumberOfDays(secid, params);
    }

    private ExternalExchangeService determineExchange(String exchangeName) {
        if(context.containsBeanDefinition(exchangeName))
           return context.getBean(exchangeName, ExternalExchangeService.class);

        throw new NoSuchExchangeException(exchangeName);
    }
}
