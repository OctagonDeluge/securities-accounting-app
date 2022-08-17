package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tink.practice.entity.Portfolio;
import ru.tink.practice.exception.PortfolioNotFoundException;
import ru.tink.practice.repository.PortfolioRepository;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public Portfolio savePortfolio(@NotBlank String portfolioName) {
        return portfolioRepository.save(new Portfolio(portfolioName, BigDecimal.valueOf(0), BigDecimal.valueOf(0)));
    }

    public Portfolio updatePortfolio(Long id, String portfolioName) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new PortfolioNotFoundException(id));
        portfolio.setName(portfolioName);
        return portfolioRepository.save(portfolio);
    }

    public void savePortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    public Portfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new PortfolioNotFoundException(id));
    }

    public void deletePortfolio(Long id) {
        portfolioRepository.findById(id)
                        .orElseThrow(() -> new PortfolioNotFoundException(id));
        portfolioRepository.deleteById(id);
    }

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }
}
