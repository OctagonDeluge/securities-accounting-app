package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.external.moex.PaymentDTO;
import ru.tink.practice.service.integration.ExchangeIntegrationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final ExchangeIntegrationService exchangeIntegrationService;

    /*
    * @GetMapping("exchange/{exchangeName}/security/{secid}")
    public List<PaymentDTO> getPaymentsForSecurity(@PathVariable String secid,
                                                   @PathVariable String exchangeName,
                                                   @RequestParam Integer page) {
        return exchangeIntegrationService.getPaymentsBySecid(exchangeName, secid, page);
    }
    * */
    @GetMapping("exchange/{exchangeName}/security/{secid}")
    public List<PaymentDTO> getPaymentsForSecurity(@PathVariable String secid,
                                                   @PathVariable String exchangeName) {
        return exchangeIntegrationService.getPaymentsBySecid(exchangeName, secid);
    }
}
