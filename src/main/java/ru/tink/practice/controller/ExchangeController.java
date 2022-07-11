package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.SecurityDTO;
import ru.tink.practice.service.integration.ExchangeIntegrationService;

import java.util.List;

@RestController
@RequestMapping("/securities/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeIntegrationService exchangeIntegrationService;

    @GetMapping
    public List<SecurityDTO> getSecuritiesByName(@RequestParam String securityName) {
        return exchangeIntegrationService.loadSecurities(securityName);
    }
}
