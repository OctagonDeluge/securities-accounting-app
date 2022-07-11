package ru.tink.practice.service.external.exchange;

import ru.tink.practice.dto.SecurityDTO;

import java.util.List;

public interface ExternalExchangeService {
    List<SecurityDTO> getSecuritiesByName(String securityName);
}
