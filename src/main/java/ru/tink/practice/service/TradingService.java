package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tink.practice.dto.request.SecurityPurchaseRequest;
import ru.tink.practice.dto.request.SecuritySaleRequest;
import ru.tink.practice.dto.response.OperationInfoDTO;
import ru.tink.practice.entity.*;
import ru.tink.practice.entity.enumeration.OperationType;
import ru.tink.practice.enumeration.Exchange;
import ru.tink.practice.enumeration.SecurityType;
import ru.tink.practice.exception.InsufficientResourceException;
import ru.tink.practice.security.SecurityUser;
import ru.tink.practice.service.scheduled.ProfitCalculationService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradingService {

    private final SecurityService securityService;
    private final UserService userService;
    private final PortfolioService portfolioService;
    private final PurchaseInfoService purchaseInfoService;
    private final ProfitCalculationService calculationService;
    private final WalletService walletService;
    private final OperationInfoService operationInfoService;

    @Transactional
    public OperationInfoDTO buySecurity(SecurityUser securityUser, SecurityPurchaseRequest purchaseRequest) {
        BigDecimal totalCost = purchaseRequest.getPurchasePrice()
                .multiply(BigDecimal.valueOf(purchaseRequest.getQuantity()));
        User user = userService.findUserByEmail(securityUser.getEmail());
        Wallet wallet = walletService
                .getWalletByIdAndClientId(purchaseRequest.getWalletId(), user.getId());

        if (wallet.getBalance().compareTo(totalCost) < 0)
            throw new InsufficientResourceException("money");

        wallet.setBalance(wallet.getBalance().subtract(totalCost));
        Portfolio portfolio = portfolioService
                .getPortfolioById(securityUser, purchaseRequest.getPortfolioId());
        Security security = securityService
                .getSecurityByPortfolioId(purchaseRequest.getPortfolioId(), purchaseRequest.getSecid());

        if (security == null) {
            security = new Security(
                    purchaseRequest.getSecid(),
                    purchaseRequest.getName(),
                    BigDecimal.valueOf(0),
                    BigDecimal.valueOf(0),
                    0,
                    SecurityType.of(purchaseRequest.getType()),
                    Exchange.of(purchaseRequest.getExchange()),
                    purchaseRequest.getCurrency(),
                    portfolio,
                    user
            );
            securityService.saveSecurity(security);
        }
        PurchaseInfo purchaseInfo = new PurchaseInfo(
                purchaseRequest.getPurchasePrice(),
                purchaseRequest.getQuantity(),
                security,
                user
        );
        purchaseInfoService.save(purchaseInfo);

        security.setTotalCost(security.getTotalCost()
                .add(totalCost));
        security.setQuantity(security.getQuantity() + purchaseRequest.getQuantity());
        portfolio.setTotalCost(portfolio.getTotalCost()
                .add(totalCost));

        OperationInfo operationInfo = new OperationInfo(
                OperationType.PURCHASE,
                security.getSecid(),
                security.getName(),
                purchaseRequest.getQuantity(),
                purchaseRequest.getPurchasePrice(),
                totalCost,
                BigDecimal.valueOf(0),
                security.getCurrency(),
                LocalDateTime.now(),
                wallet,
                user
        );

        operationInfoService.save(operationInfo);

        return new OperationInfoDTO(
                security.getSecid(),
                purchaseRequest.getQuantity(),
                purchaseRequest.getPurchasePrice(),
                totalCost,
                security.getCurrency(),
                wallet.getBalance()
        );
    }

    @Transactional
    //:TODO validate saleRequest quantity !< 1 price !<0
    public OperationInfoDTO sellSecurity(SecurityUser securityUser, SecuritySaleRequest saleRequest) {
        Security security = securityService
                .getSecurity(securityUser, saleRequest.getSecurityId());

        if (security.getQuantity() < saleRequest.getQuantity())
            throw new InsufficientResourceException("securities");

        User user = security.getClient();
        Wallet wallet = walletService
                .getWalletByIdAndClientId(saleRequest.getWalletId(), user.getId());
        Portfolio portfolio = security.getPortfolio();
        List<PurchaseInfo> pis = security.getPurchaseInfos();
        BigDecimal start = BigDecimal.valueOf(0);
        calculationService.calculateSecurityProfit(security.getId(), saleRequest.getPrice());
        int toSale = saleRequest.getQuantity();
        while (toSale > 0) {
            int index = pis.size() - 1;
            PurchaseInfo info = pis.get(index);
            if (toSale < info.getQuantity()) {
                pis.get(index).setQuantity(info.getQuantity() - toSale);
                toSale -= info.getQuantity();
                start = start.add(info.getPrice().multiply(BigDecimal.valueOf(info.getQuantity())));
            }
            else {
                start = start.add(info.getPrice().multiply(BigDecimal.valueOf(info.getQuantity())));
                toSale -= info.getQuantity();
                pis.remove(index);
            }
        }
        BigDecimal salePrice = saleRequest.getPrice()
                .multiply(BigDecimal.valueOf(saleRequest.getQuantity()));
        wallet.setBalance(wallet.getBalance().add(salePrice));

        if(pis.size() == 0) {
            securityService.deleteSecurity(securityUser, security.getId());
        }
        else {
            security.setTotalCost(security.getTotalCost().subtract(salePrice));
            security.setQuantity(pis.size());
        }
        portfolio.setTotalCost(portfolio.getTotalCost().subtract(salePrice));

        OperationInfo operationInfo = new OperationInfo(
                OperationType.SALE,
                security.getSecid(),
                security.getName(),
                saleRequest.getQuantity(),
                saleRequest.getPrice(),
                salePrice,
                salePrice.subtract(start),
                security.getCurrency(),
                LocalDateTime.now(),
                wallet,
                user
        );

        operationInfoService.save(operationInfo);

        return new OperationInfoDTO(
                security.getSecid(),
                saleRequest.getQuantity(),
                saleRequest.getPrice(),
                salePrice,
                security.getCurrency(),
                wallet.getBalance()
        );
    }
}
