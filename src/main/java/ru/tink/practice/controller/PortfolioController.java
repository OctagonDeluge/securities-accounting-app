package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.entity.Portfolio;
import ru.tink.practice.security.SecurityUser;
import ru.tink.practice.service.PortfolioService;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
@Slf4j
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping
    public List<Portfolio> getPortfolios(Authentication authentication) {
        return portfolioService.getAllPortfolios((SecurityUser) authentication.getPrincipal());
    }

    @PostMapping
    public Portfolio createPortfolio(Authentication authentication, @RequestParam String portfolioName) {
        return portfolioService.savePortfolio((SecurityUser) authentication.getPrincipal(), portfolioName);
    }

    @PutMapping("/{id}")
    public Portfolio updatePortfolio(Authentication authentication,
                                     @RequestParam String portfolioName, @PathVariable Long id) {
        return portfolioService.updatePortfolio((SecurityUser) authentication.getPrincipal(), id, portfolioName);
    }

    @DeleteMapping("/{id}")
    public void deletePortfolio(Authentication authentication, @PathVariable Long id) {
        portfolioService.deletePortfolio((SecurityUser) authentication.getPrincipal(), id);
    }
}
