package ru.tink.practice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tink.practice.enumeration.Exchange;
import ru.tink.practice.enumeration.SecurityType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityResponseDTO {
    private Long id;
    private String secid;
    private String name;
    private Double profit;
    private SecurityType type;
    private Exchange exchange;
    private List<PurchaseInfoDTO> purchaseInfos;
}
