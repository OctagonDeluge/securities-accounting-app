package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.entity.Portfolio;
import ru.tink.practice.service.PortfolioService;
import ru.tink.practice.service.scheduled.ProfitCalculationService;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final ProfitCalculationService profitCalculationService;

    @GetMapping
    public List<Portfolio> getPortfolios() {
        return portfolioService.getAllPortfolios();
    }

    @PostMapping
    public void createPortfolio(@RequestParam String portfolioName) {
        portfolioService.savePortfolio(portfolioName);
    }

    @GetMapping("/test")
    public void test() {
        profitCalculationService.calculateProfit();
    }
}
