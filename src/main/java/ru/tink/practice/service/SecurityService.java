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
import ru.tink.practice.security.SecurityUser;

import javax.transaction.Transactional;
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

    @Transactional
    public SecurityResponseDTO saveSecurity(SecurityUser securityUser, @Valid SecurityDTO securityDTO) {
        Security security =
                securityRepository
                        .findByPortfolioIdAndSecid(securityDTO.getPortfolioId(),securityDTO.getSecid());
        Portfolio portfolio =
                portfolioService.getPortfolioById(securityUser, securityDTO.getPortfolioId());
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

    @Transactional
    public void deleteSecurity(SecurityUser securityUser, Long id) {
        //findByUsernameAndId
        Security security = securityRepository
                .findByIdAndClientId(id, securityUser.getId())
                .orElseThrow(() -> new SecurityNotFoundException(id));
        securityRepository.deleteById(id);
        Portfolio portfolio = security.getPortfolio();
        portfolio.setTotalCost(portfolio.getTotalCost().subtract(security.getTotalCost()));
        portfolioService.savePortfolio(portfolio);
    }

    public List<SecurityResponseDTO> getSecuritiesDTO(SecurityUser securityUser, Long portfolioId) {
        List<SecurityResponseDTO> securities = new ArrayList<>();
        securityRepository
                .findAllByPortfolioIdAndClientId(portfolioId, securityUser.getId())
                .forEach(security -> securities.add(new SecurityResponseDTO(security)));
        return securities;
    }

    public List<Security> getSecurities(SecurityUser securityUser, Long portfolioId) {
        return securityRepository
                .findAllByPortfolioIdAndClientId(portfolioId, securityUser.getId());
    }

    public List<StatisticDTO> getNumberOfSecuritiesOfEachType(SecurityUser securityUser, Long portfolioId) {
        return securityRepository.countBySecurityType(portfolioId, securityUser.getId());
    }

    @Transactional
    public SecurityResponseDTO getSecurity(SecurityUser securityUser, Long id) {
        return new SecurityResponseDTO(securityRepository.findByIdAndClientId(id, securityUser.getId())
                .orElseThrow(() -> new SecurityNotFoundException(id)));
    }
}
