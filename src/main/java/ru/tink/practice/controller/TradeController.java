package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.request.SecurityPurchaseRequest;
import ru.tink.practice.dto.request.SecuritySaleRequest;
import ru.tink.practice.dto.response.OperationInfoDTO;
import ru.tink.practice.entity.OperationInfo;
import ru.tink.practice.security.SecurityUser;
import ru.tink.practice.service.TradingService;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
@Slf4j
public class TradeController {

    private final TradingService tradingService;

    @PostMapping("/security")
    public OperationInfoDTO buySecurity(Authentication authentication, @RequestBody SecurityPurchaseRequest purchaseRequest) {
        return tradingService.buySecurity((SecurityUser) authentication.getPrincipal(), purchaseRequest);
    }

    @DeleteMapping("/security")
    public OperationInfoDTO sellSecurity(Authentication authentication, @RequestBody SecuritySaleRequest saleRequest) {
        log.info(saleRequest.getWalletId().toString());
        return tradingService.sellSecurity((SecurityUser) authentication.getPrincipal(), saleRequest);
    }
}
