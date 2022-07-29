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
            double portfolioProfit = 0.0;
            List<Security> securities =
                    securityRepository.findAllByPortfolioId(portfolio.getId());
            for (Security security : securities) {
                double securityProfit = 0.0;
                for(PurchaseInfo purchaseInfo: security.getPurchaseInfos()) {
                    Double currentPrice = exchangeIntegrationService
                            .getCurrentSecurityPrice(security.getSecid(),
                                    security.getType().getName(),
                                    security.getExchange().getName());
                    log.info(currentPrice.toString());
                    double purchaseProfit
                            = (currentPrice - purchaseInfo.getPrice())*purchaseInfo.getQuantity();
                    log.info(Double.toString(purchaseProfit));
                    securityProfit += purchaseProfit;
                }
                security.setProfit(securityProfit);
                log.info(security.getProfit().toString());
                securityRepository.save(security);
                portfolioProfit += securityProfit;
            }
            portfolio.setProfit(portfolioProfit);
            log.info(portfolio.getProfit().toString());
            portfolioRepository.save(portfolio);
        }
    }
}
