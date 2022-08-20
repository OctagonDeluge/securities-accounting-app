package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tink.practice.dto.SecurityDTO;
import ru.tink.practice.dto.response.SecurityResponseDTO;
import ru.tink.practice.dto.response.StatisticDTO;
import ru.tink.practice.entity.Portfolio;
import ru.tink.practice.entity.PurchaseInfo;
import ru.tink.practice.entity.Security;
import ru.tink.practice.enumeration.Exchange;
import ru.tink.practice.enumeration.SecurityType;
import ru.tink.practice.exception.SecurityNotFoundException;
import ru.tink.practice.repository.SecurityRepository;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Validated
@Service
public class SecurityService {

    private final SecurityRepository securityRepository;
    private final PortfolioService portfolioService;

    public SecurityResponseDTO saveSecurity(@Valid SecurityDTO securityDTO) {
        Security security =
                securityRepository
                        .findByPortfolioIdAndSecid(securityDTO.getPortfolioId(),securityDTO.getSecid());
        Portfolio portfolio =
                portfolioService.getPortfolioById(securityDTO.getPortfolioId());
        if (security == null) {
            security = new Security();
            security.setSecid(securityDTO.getSecid());
            security.setName(securityDTO.getName());
            security.setTotalCost(BigDecimal.valueOf(0));
            security.setProfit(BigDecimal.valueOf(0));
            security.setExchange(Exchange.of(securityDTO.getExchange()));
            security.setType(SecurityType.of(securityDTO.getType()));
            security.setPortfolio(portfolio);
            security.setCurrency(securityDTO.getCurrency());
            security.setPurchaseInfos(new ArrayList<>());
        }
        PurchaseInfo purchaseInfo = new PurchaseInfo();
        purchaseInfo.setPurchaseDate(LocalDateTime.now());
        purchaseInfo.setPrice(securityDTO.getPurchasePrice());
        purchaseInfo.setQuantity(securityDTO.getQuantity());
        purchaseInfo.setCurrency(securityDTO.getCurrency());
        purchaseInfo.setSecurity(security);

        BigDecimal totalCost = purchaseInfo.getPrice().multiply(BigDecimal.valueOf(purchaseInfo.getQuantity()));
        security.setTotalCost(security.getTotalCost()
                .add(totalCost));
        portfolio.setTotalCost(portfolio.getTotalCost()
                .add(totalCost));

        security.getPurchaseInfos().add(purchaseInfo);
        portfolio.getSecurities().add(security);
        portfolioService.savePortfolio(portfolio);
        return new SecurityResponseDTO(security);
    }

    public void saveSecurity(Security security) {
        securityRepository.save(security);
    }

    public void deleteSecurity(Long id) {
        Security security = securityRepository.findById(id)
                .orElseThrow(() -> new SecurityNotFoundException(id));
        securityRepository.deleteById(id);
        Portfolio portfolio = security.getPortfolio();
        portfolio.setTotalCost(portfolio.getTotalCost().subtract(security.getTotalCost()));
        portfolioService.savePortfolio(portfolio);
    }

    public List<SecurityResponseDTO> getSecuritiesDTO(Long portfolioId) {
        List<SecurityResponseDTO> securities = new ArrayList<>();
        securityRepository.findAllByPortfolioId(portfolioId)
                .forEach(security -> securities.add(new SecurityResponseDTO(security)));
        return securities;
    }

    public List<Security> getSecurities(Long portfolioId) {
        return securityRepository.findAllByPortfolioId(portfolioId);
    }

    public List<StatisticDTO> getNumberOfSecuritiesOfEachType(Long portfolioId) {
        return securityRepository.countBySecurityType(portfolioId);
    }

    public SecurityResponseDTO getSecurity(Long id) {
        return new SecurityResponseDTO(securityRepository.findById(id)
                .orElseThrow(() -> new SecurityNotFoundException(id)));
    }
}
