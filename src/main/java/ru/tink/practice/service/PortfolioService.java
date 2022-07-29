package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.entity.Portfolio;
import ru.tink.practice.repository.PortfolioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public void savePortfolio(String portfolioName) {
        portfolioRepository.save(new Portfolio(portfolioName, 0.0));
    }

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }
}
