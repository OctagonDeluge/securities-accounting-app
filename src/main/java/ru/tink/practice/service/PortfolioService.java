package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tink.practice.entity.Portfolio;
import ru.tink.practice.entity.User;
import ru.tink.practice.exception.PortfolioIsNotEmptyException;
import ru.tink.practice.exception.PortfolioNotFoundException;
import ru.tink.practice.repository.PortfolioRepository;
import ru.tink.practice.security.SecurityUser;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserService userService;

    public Portfolio savePortfolio(SecurityUser securityUser, @NotBlank String portfolioName) {
        User user = userService.findUserByEmail(securityUser.getEmail());
        return portfolioRepository.save(new Portfolio(portfolioName,
                BigDecimal.valueOf(0),
                user));
    }

    public Portfolio updatePortfolio(SecurityUser securityUser, Long id, @NotBlank String portfolioName) {
        Portfolio portfolio = portfolioRepository.findByIdAndClientId(id, securityUser.getId())
                .orElseThrow(() -> new PortfolioNotFoundException(id));
        portfolio.setName(portfolioName);
        return portfolioRepository.save(portfolio);
    }

    public void savePortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    public Portfolio getPortfolioById(SecurityUser securityUser, Long id) {
        return portfolioRepository.findByIdAndClientId(id, securityUser.getId())
                .orElseThrow(() -> new PortfolioNotFoundException(id));
    }

    @Transactional
    public void deletePortfolio(SecurityUser securityUser, Long id) {
        Portfolio portfolio = portfolioRepository
                .findByIdAndClientId(id, securityUser.getId())
                .orElseThrow(() -> new PortfolioNotFoundException(id));
        if(!portfolio.getSecurities().isEmpty())
            throw new PortfolioIsNotEmptyException(portfolio.getName());
        portfolioRepository.deleteById(id);
    }

    public Page<Portfolio> getAllPortfolios(SecurityUser user, Pageable pageable) {
        return portfolioRepository.findAllByClientIdOrderByIdDesc(user.getId(), pageable);
    }

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }
}
