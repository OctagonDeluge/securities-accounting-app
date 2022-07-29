package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.entity.PurchaseInfo;
import ru.tink.practice.entity.Security;
import ru.tink.practice.repository.PurchaseInfoRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseInfoService {

    private final PurchaseInfoRepository purchaseInfoRepository;

    public void savePurchaseInfo(LocalDateTime purchaseDate,
                                 Double price,
                                 Integer quantity,
                                 Security security) {
        PurchaseInfo purchaseInfo = new PurchaseInfo();
        purchaseInfo.setPurchaseDate(purchaseDate);
        purchaseInfo.setPrice(price);
        purchaseInfo.setQuantity(quantity);
        purchaseInfo.setSecurity(security);
        purchaseInfoRepository.save(purchaseInfo);
    }
}
