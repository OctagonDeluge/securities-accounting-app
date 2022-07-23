package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.external.moex.SecurityFullInfoDTO;
import ru.tink.practice.dto.external.moex.SecurityShortInfoDTO;
import ru.tink.practice.entity.Security;
import ru.tink.practice.service.SecurityService;
import ru.tink.practice.service.integration.ExchangeIntegrationService;

import java.util.List;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeIntegrationService exchangeIntegrationService;
    private final SecurityService securityService;

    @GetMapping
    public List<SecurityShortInfoDTO> getSecuritiesByName(@RequestParam String securityName) {
        return exchangeIntegrationService.loadSecurities(securityName);
    }

    @GetMapping("/{secid}")
    public SecurityFullInfoDTO getSecurityBySecid(@PathVariable String secid, @RequestParam String exchangeName) {
        return exchangeIntegrationService.getSecurityBySecid(secid, exchangeName);
    }

    @PostMapping
    public void addSecurity(@PathVariable String portfolioId, @RequestBody Security security) {
        //securityService.saveSecurity(securityDTO, quantity, purchasePrice);
    }
}
