package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.response.OperationDTO;
import ru.tink.practice.entity.OperationInfo;
import ru.tink.practice.security.SecurityUser;
import ru.tink.practice.service.OperationInfoService;
import ru.tink.practice.service.WalletService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {

    private final OperationInfoService operationInfoService;
    private final WalletService walletService;
    private final Integer PAGE_SIZE = 20;

    /*@GetMapping("/{page}")
    public List<OperationInfo> getHistory(Authentication authentication, @RequestParam Long walletId, @PathVariable Integer page) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        walletService.getWalletByIdAndClientId(walletId, securityUser.getId());
        return operationInfoService.getAllByWalletId(walletId, PageRequest.of(page, PAGE_SIZE));
    }*/

    @GetMapping
    public List<OperationDTO> getHistory(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return operationInfoService.getAllByClientId(securityUser.getId());
    }

    @DeleteMapping
    public void deleteHistory(Authentication authentication, @RequestParam Long fromDateMs, @RequestParam Long toDateMs) {
        LocalDateTime fromDate = LocalDateTime.ofEpochSecond(fromDateMs, 0, ZoneOffset.UTC);
        LocalDateTime toDate = LocalDateTime.ofEpochSecond(toDateMs, 0, ZoneOffset.UTC);
        operationInfoService.deleteHistoryByDate((SecurityUser) authentication.getPrincipal(), fromDate, toDate);
    }
}
