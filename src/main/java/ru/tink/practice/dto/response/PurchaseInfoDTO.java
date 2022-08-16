package ru.tink.practice.dto.response;

import lombok.Data;
import ru.tink.practice.entity.PurchaseInfo;

@Data
public class PurchaseInfoDTO {
    private Long id;

    public PurchaseInfoDTO(PurchaseInfo purchaseInfo) {
        id = purchaseInfo.getId();
    }
}