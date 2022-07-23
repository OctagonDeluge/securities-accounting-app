package ru.tink.practice.service.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.dto.external.moex.SecurityDTO;
import ru.tink.practice.exception.NoSuchExchangeException;
import ru.tink.practice.exception.SecurityNotFoundException;
import ru.tink.practice.exception.SecurityNotFoundInExternalServiceException;
import ru.tink.practice.service.external.exchange.ExternalExchangeService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeIntegrationService {

    private final List<ExternalExchangeService> exchangeServices;

    public List<SecurityDTO> loadSecurities(String securityName) {
        List<SecurityDTO> securities = new ArrayList<>();
        exchangeServices.forEach(service -> securities.addAll(service.getSecuritiesByName(securityName)));
        return securities;
    }

    public SecurityDTO getSecurityBySecid(String secid, String exchangeName) {
        for (ExternalExchangeService service:
             exchangeServices) {
            if(service.getServiceName().equals(exchangeName)) {
                return service.getSecurityBySecid(secid);
            }
        }
        throw new NoSuchExchangeException(exchangeName);
    }
}
