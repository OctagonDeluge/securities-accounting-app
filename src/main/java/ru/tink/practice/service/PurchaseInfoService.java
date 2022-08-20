package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.entity.PurchaseInfo;
import ru.tink.practice.repository.PurchaseInfoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseInfoService {

    private final PurchaseInfoRepository purchaseInfoRepository;

    public List<PurchaseInfo> getAllPurchaseInfosById(List<Long> purchaseIds) {
        return purchaseInfoRepository.findAllById(purchaseIds);
    }
}
