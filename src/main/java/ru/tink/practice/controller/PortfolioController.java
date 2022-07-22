package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.entity.Portfolio;
import ru.tink.practice.service.PortfolioService;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    public List<Portfolio> getPortfolios() {
        return portfolioService.getAllPortfolios();
    }

    @PostMapping
    public void createPortfolio(@RequestParam String portfolioName) {
        portfolioService.savePortfolio(portfolioName);
    }
}
