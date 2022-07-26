package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.external.moex.SecurityFullInfoDTO;
import ru.tink.practice.dto.external.moex.SecurityShortInfoDTO;
import ru.tink.practice.service.integration.ExchangeIntegrationService;

import java.util.List;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeIntegrationService exchangeIntegrationService;

    @GetMapping
    public List<SecurityShortInfoDTO> getSecuritiesByName(@RequestParam String securityName) {
        return exchangeIntegrationService.loadSecurities(securityName);
    }

    @GetMapping("{exchangeName}/security/{secid}")
    public SecurityFullInfoDTO getSecurityBySecid(@PathVariable String exchangeName, @PathVariable String secid) {
        return exchangeIntegrationService.getSecurityBySecid(secid, exchangeName);
    }
}
