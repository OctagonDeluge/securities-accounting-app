package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.entity.Portfolio;
import ru.tink.practice.exception.PortfolioNotFoundException;
import ru.tink.practice.repository.PortfolioRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public Portfolio savePortfolio(String portfolioName) {
        return portfolioRepository.save(new Portfolio(portfolioName, BigDecimal.valueOf(0)));
    }

    public Portfolio updatePortfolio(Long id, String portfolioName) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new PortfolioNotFoundException(id));
        portfolio.setName(portfolioName);
        return portfolioRepository.save(portfolio);
    }

    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }
}
