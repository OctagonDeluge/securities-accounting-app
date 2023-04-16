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

    /*@GetMapping("/{securityName}")
    public List<SecurityShortInfoDTO> getSecuritiesByName(@PathVariable String securityName) {
        return exchangeIntegrationService.loadSecuritiesByName(securityName);
    }*/

    @GetMapping("{exchangeName}/security/{secid}")
    public SecurityFullInfoDTO getSecurityBySecid(@PathVariable String exchangeName, @PathVariable String secid) {
        return exchangeIntegrationService.getSecurityBySecid(secid, exchangeName);
    }

    @GetMapping("{exchangeName}/security")
    public List<SecurityShortInfoDTO> getSecuritiesByExchange(@PathVariable String exchangeName, @RequestParam String securityName) {
        return exchangeIntegrationService.loadSecuritiesByExchange(securityName, exchangeName);
    }
}
