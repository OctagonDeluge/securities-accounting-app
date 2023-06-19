package ru.tink.practice.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tink.practice.dto.external.moex.CouponsDTO;
import ru.tink.practice.dto.external.moex.DividendDTO;
import ru.tink.practice.dto.external.moex.Payment;
import ru.tink.practice.entity.*;
import ru.tink.practice.entity.enumeration.OperationType;
import ru.tink.practice.repository.UserRepository;
import ru.tink.practice.service.OperationInfoService;
import ru.tink.practice.service.integration.ExchangeIntegrationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentAccrualService {
    private final ExchangeIntegrationService exchangeIntegrationService;
    private final OperationInfoService operationInfoService;
    private final UserRepository userRepository;

    @Transactional
    public void paymentsAccrual() {
        List<User> users = userRepository.findAll();
        for (User user :
                users) {
            Wallet wallet = user.getWallet();
            for (Portfolio portfolio :
                    user.getPortfolios()) {
                for (Security security :
                        portfolio.getSecurities()) {
                    BigDecimal quantity = BigDecimal.valueOf(security.getQuantity());
                    if (security.getType().getName().equals("shares")) {
                        List<DividendDTO> dividends =
                                exchangeIntegrationService.getDividends(security.getExchange().getName(), security.getSecid());
                        if(dividends.size() > 0) {
                            DividendDTO div = dividends.get(dividends.size() - 1);
                            if (div.getDividendDate().equals(LocalDate.now())) {
                                BigDecimal paymentSum = div.getCost().multiply(quantity);
                                wallet.setBalance(wallet.getBalance().add(paymentSum));
                                operationInfoService.save(new OperationInfo(OperationType.DIVIDEND, security.getSecid(), security.getName(), security.getQuantity(),
                                        div.getCost(), paymentSum, paymentSum, security.getCurrency(),
                                        LocalDateTime.now(), wallet, user));
                            }
                        }
                    } else {
                        CouponsDTO coupons =
                                exchangeIntegrationService.getCoupons(security.getExchange().getName(), security.getSecid());

                        int couponId = dateBinarySearch(coupons.getCoupons());
                        int amortId = dateBinarySearch(coupons.getAmortizations());
                        if (!(couponId < 0) && coupons.getCoupons().size() > 0) {
                            Payment coupon = coupons.getCoupons().get(couponId);
                            wallet.setBalance(wallet.getBalance().add(coupon.getPaymentCost().multiply(quantity)));
                            operationInfoService.save(new OperationInfo(OperationType.COUPON, security.getSecid(), security.getName(), security.getQuantity(),
                                    coupon.getPaymentCost(), coupon.getPaymentCost().multiply(quantity), coupon.getPaymentCost().multiply(quantity), security.getCurrency(),
                                    LocalDateTime.now(), wallet, user));
                        }
                        if(!(amortId < 0) && coupons.getAmortizations().size() > 0) {
                            Payment amortization = coupons.getAmortizations().get(amortId);
                            wallet.setBalance(wallet.getBalance().add(amortization.getPaymentCost().multiply(quantity)));
                            operationInfoService.save(new OperationInfo(OperationType.COUPON, security.getSecid(), security.getName(), security.getQuantity(),
                                    amortization.getPaymentCost(), amortization.getPaymentCost().multiply(quantity), amortization.getPaymentCost().multiply(quantity),
                                    security.getCurrency(), LocalDateTime.now(), wallet, user));
                        }
                    }
                }
            }
        }
    }

    public int dateBinarySearch(List<? extends Payment> list) {
        int left = 0;
        int right = list.size();
        int cursor = 0;
        LocalDate today = LocalDate.now();
        while (left <= right) {
            cursor = (left + right) / 2;
            LocalDate paymentDate = list.get(cursor).getPaymentDate();
            if (paymentDate.isBefore(today)) {
                left = cursor + 1;
            } else if (paymentDate.isAfter(today)) {
                right = cursor - 1;
            } else {
                return cursor;
            }
        }
        return -1;
    }
}
