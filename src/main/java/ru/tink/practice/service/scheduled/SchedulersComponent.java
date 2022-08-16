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

    @Scheduled(cron = "0/10 * * * * *")
    public void calculateProfit() {
        //log.info("start calculating profit");
        profitCalculationService.calculateProfit();
        //log.info("profit calculated");
    }
}
