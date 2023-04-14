package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.tink.practice.entity.PurchaseInfo;
import ru.tink.practice.repository.PurchaseInfoRepository;
import ru.tink.practice.security.SecurityUser;

@Service
@RequiredArgsConstructor
public class PurchaseInfoService {

    private final PurchaseInfoRepository purchaseInfoRepository;

    public Page<PurchaseInfo> getAllPurchaseInfosById(Long securityId, SecurityUser securityUser, Pageable pageable) {
        return purchaseInfoRepository
                .findAllBySecurityIdAndClientId(securityId, securityUser.getId(), pageable);
    }
}
