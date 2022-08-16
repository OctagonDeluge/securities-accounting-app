package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.response.StatisticDTO;
import ru.tink.practice.dto.external.moex.CurrentPriceDTO;
import ru.tink.practice.service.SecurityService;
import ru.tink.practice.service.integration.ExchangeIntegrationService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {
    private final ExchangeIntegrationService exchangeIntegrationService;
    private final SecurityService securityService;

    @GetMapping("exchange/{exchangeName}/security/{secid}")
    public List<CurrentPriceDTO> getPricesForNumberOfDays(@PathVariable String exchangeName,
                                                          @PathVariable String secid,
                                                          @RequestParam Map<String, String> params) {
        return exchangeIntegrationService
                .getPricesForNumberOfDays(secid, exchangeName, params);
    }

    @GetMapping("portfolio/{portfolioId}")
    public List<StatisticDTO> getCount(@PathVariable Long portfolioId) {
        return securityService.getNumberOfSecuritiesOfEachType(portfolioId);
    }
}
