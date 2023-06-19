package ru.tink.practice.service.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tink.practice.entity.Portfolio;
import ru.tink.practice.entity.PurchaseInfo;
import ru.tink.practice.entity.Security;
import ru.tink.practice.service.PortfolioService;
import ru.tink.practice.service.SecurityService;
import ru.tink.practice.service.integration.ExchangeIntegrationService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfitCalculationService {

    private final ExchangeIntegrationService exchangeIntegrationService;
    private final SecurityService securityService;
    private final PortfolioService portfolioService;

    @Transactional
    public void calculateProfit() {
        List<Portfolio> portfolios = portfolioService.getAllPortfolios();
        for (Portfolio portfolio : portfolios) {
            BigDecimal portfolioPrice = BigDecimal.valueOf(0);
            List<Security> securities =
                    portfolio.getSecurities();
            for (Security security : securities) {
                BigDecimal securityProfit = BigDecimal.valueOf(0);
                BigDecimal securityPrice = BigDecimal.valueOf(0);
                BigDecimal currentSecurityPrice = exchangeIntegrationService
                        .getCurrentSecurityPrice(security.getSecid(),
                                security.getType().getName(),
                                security.getExchange().getName());
                for (PurchaseInfo purchaseInfo : security.getPurchaseInfos()) {
                    BigDecimal purchaseProfit
                            = (currentSecurityPrice.subtract(purchaseInfo.getPrice())).multiply(BigDecimal.valueOf(purchaseInfo.getQuantity()));
                    log.info(purchaseProfit.toString());
                    securityProfit = securityProfit.add(purchaseProfit);
                    securityPrice = securityPrice.add(purchaseInfo.getPrice().multiply(BigDecimal.valueOf(purchaseInfo.getQuantity())));
                    securityPrice = securityPrice.add(purchaseProfit);
                }
                security.setTotalCost(securityPrice);
                security.setProfit(securityProfit);
                securityService.saveSecurity(security);
                portfolioPrice = portfolioPrice.add(securityPrice);
            }
            portfolio.setTotalCost(portfolioPrice);
        }
    }

    @Transactional
    public void calculateSecurityProfit(Long securityId, BigDecimal priceOnSelling) {
        Security security = securityService.getSecurity(securityId);
        Portfolio portfolio = security.getPortfolio();
        BigDecimal securityProfit = BigDecimal.valueOf(0);
        for (PurchaseInfo purchaseInfo : security.getPurchaseInfos()) {
            BigDecimal purchaseProfit
                    = priceOnSelling.subtract(purchaseInfo.getPrice());
            securityProfit = securityProfit.add(purchaseProfit);
        }
        security.setTotalCost(security.getTotalCost().add(securityProfit));
        portfolio.setTotalCost(portfolio.getTotalCost().add(securityProfit));
    }
}
