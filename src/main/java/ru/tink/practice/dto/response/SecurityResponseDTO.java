package ru.tink.practice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tink.practice.entity.Security;
import ru.tink.practice.enumeration.Exchange;
import ru.tink.practice.enumeration.SecurityType;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityResponseDTO {
    private Long id;
    private String secid;
    private String name;
    private Double currentPrice;
    private Double profit;
    private SecurityType type;
    private Exchange exchange;
    private List<PurchaseInfoDTO> purchaseInfos;

    public SecurityResponseDTO(Security security) {
        purchaseInfos = new ArrayList<>();
        id = security.getId();
        secid = security.getSecid();
        name = security.getName();
        currentPrice = security.getCurrentPrice();
        profit = security.getProfit();
        type = security.getType();
        exchange = security.getExchange();
        security.getPurchaseInfos().forEach(purchaseInfo -> {
            purchaseInfos.add(new PurchaseInfoDTO(purchaseInfo));
        });
    }
}
