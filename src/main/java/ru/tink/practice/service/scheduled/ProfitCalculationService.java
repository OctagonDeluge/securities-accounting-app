package ru.tink.practice.service.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tink.practice.entity.Portfolio;
import ru.tink.practice.entity.PurchaseInfo;
import ru.tink.practice.entity.Security;
import ru.tink.practice.repository.PortfolioRepository;
import ru.tink.practice.repository.SecurityRepository;
import ru.tink.practice.service.integration.ExchangeIntegrationService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfitCalculationService {

    private final ExchangeIntegrationService exchangeIntegrationService;
    private final SecurityRepository securityRepository;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public void calculateProfit() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        for (Portfolio portfolio : portfolios) {
            BigDecimal portfolioProfit = BigDecimal.valueOf(0);
            BigDecimal portfolioPrice = BigDecimal.valueOf(0);
            List<Security> securities =
                    securityRepository.findAllByPortfolioId(portfolio.getId());
            for (Security security : securities) {
                BigDecimal securityProfit = BigDecimal.valueOf(0);
                BigDecimal securityPrice = BigDecimal.valueOf(0);
                BigDecimal currentSecurityPrice = exchangeIntegrationService
                        .getCurrentSecurityPrice(security.getSecid(),
                                security.getType().getName(),
                                security.getExchange().getName());
                log.info(currentSecurityPrice.toString());
                for(PurchaseInfo purchaseInfo: security.getPurchaseInfos()) {
                    //BigDecimal
                    BigDecimal purchaseProfit
                            = (currentSecurityPrice.subtract(purchaseInfo.getPrice())).multiply(BigDecimal.valueOf(purchaseInfo.getQuantity()));
                    log.info(purchaseInfo.toString());
                    securityProfit = securityProfit.add(purchaseProfit);
                    securityPrice = securityPrice.add(purchaseInfo.getPrice().multiply(BigDecimal.valueOf(purchaseInfo.getQuantity())));
                }
                security.setTotalCost(currentSecurityPrice);
                security.setProfit(securityProfit);
                log.info(security.getProfit().toString());
                securityRepository.save(security);
                portfolioPrice = portfolioPrice.add(securityPrice);
                portfolioProfit = portfolioProfit.add(securityProfit);
            }
            portfolio.setTotalCost(portfolioPrice);
            portfolio.setProfit(portfolioProfit);
            log.info(portfolio.getProfit().toString());
            portfolioRepository.save(portfolio);
        }
    }
}
