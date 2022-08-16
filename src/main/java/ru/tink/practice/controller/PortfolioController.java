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
    public Portfolio createPortfolio(@RequestParam String portfolioName) {
        return portfolioService.savePortfolio(portfolioName);
    }

    @PutMapping("/{id}")
    public Portfolio updatePortfolio(@RequestParam String portfolioName, @PathVariable Long id) {
        return portfolioService.updatePortfolio(id, portfolioName);
    }

    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
    }
}
