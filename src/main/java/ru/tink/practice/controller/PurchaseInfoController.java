package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.entity.PurchaseInfo;
import ru.tink.practice.security.SecurityUser;
import ru.tink.practice.service.PurchaseInfoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchaseInfo")
public class PurchaseInfoController {

    private final Integer PAGE_SIZE = 5;

    private final PurchaseInfoService purchaseInfoService;

    @GetMapping("/security/{securityId}")
    public List<PurchaseInfo> getPurchaseInfos(Authentication authentication, @PathVariable Long securityId, @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return purchaseInfoService
                .getAllPurchaseInfosById(securityId, (SecurityUser) authentication.getPrincipal(), pageable)
                .getContent();
    }

}
