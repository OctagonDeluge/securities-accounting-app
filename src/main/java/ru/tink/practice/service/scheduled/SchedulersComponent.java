package ru.tink.practice.service.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulersComponent {

    private final ProfitCalculationService profitCalculationService;
    private final PaymentAccrualService paymentAccrualService;

    @Scheduled(cron = "0 0/5 * * * *")
    public void calculateProfit() {
        log.info("profit calculation");
        profitCalculationService.calculateProfit();
    }

    @Scheduled(cron = "0 0 5 * * *")
    public void calculatePayments() {
        log.info("payments calculation");
        paymentAccrualService.paymentsAccrual();
    }
}
