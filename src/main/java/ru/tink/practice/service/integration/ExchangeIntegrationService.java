package ru.tink.practice.service.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.dto.SecurityDTO;
import ru.tink.practice.service.external.exchange.ExternalExchangeService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeIntegrationService {

    private final List<ExternalExchangeService> exchangeServices;
    private List<SecurityDTO> securities;

    public List<SecurityDTO> loadSecurities(String securityName) {
        securities = new ArrayList<>();
        exchangeServices.forEach(service -> securities.addAll(service.getSecuritiesByName(securityName)));
        return securities;
    }
}
