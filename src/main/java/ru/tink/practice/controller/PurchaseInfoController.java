package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tink.practice.entity.PurchaseInfo;
import ru.tink.practice.service.PurchaseInfoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchaseInfo")
public class PurchaseInfoController {

    private final PurchaseInfoService purchaseInfoService;

    @GetMapping
    public List<PurchaseInfo> getPurchaseInfos(@RequestParam List<Long> purchaseIds) {
        return purchaseInfoService.getAllPurchaseInfosById(purchaseIds);
    }

}
