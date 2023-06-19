package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.external.moex.SecurityFullInfoDTO;
import ru.tink.practice.dto.external.moex.SecurityShortInfoDTO;
import ru.tink.practice.service.integration.ExchangeIntegrationService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
@Slf4j
public class ExchangeController {

    private final ExchangeIntegrationService exchangeIntegrationService;

    @GetMapping("{exchangeName}/security/{secid}")
    public SecurityFullInfoDTO getSecurityBySecid(@PathVariable String exchangeName, @PathVariable String secid) {
        return exchangeIntegrationService
                .getSecurityBySecid(secid, exchangeName);
    }

    @GetMapping("{exchangeName}/securities")
    public List<SecurityShortInfoDTO> getSecuritiesByExchange(@PathVariable String exchangeName,
                                                              @RequestParam String securityName) {
        return exchangeIntegrationService
                .loadSecuritiesByExchange(exchangeName, securityName);
    }

    @GetMapping("{exchangeName}/price/{secid}")
    public ResponseEntity<BigDecimal> getCurrentSecurityPrice(@PathVariable String exchangeName,
                                                  @PathVariable String secid,
                                                  @RequestParam String securityType) {
        return ResponseEntity.ok(exchangeIntegrationService
                .getCurrentSecurityPrice(secid, securityType, exchangeName));
    }
}
