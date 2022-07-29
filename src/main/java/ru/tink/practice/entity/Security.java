package ru.tink.practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tink.practice.dto.response.PurchaseInfoDTO;
import ru.tink.practice.dto.response.SecurityResponseDTO;
import ru.tink.practice.enumeration.Exchange;
import ru.tink.practice.enumeration.SecurityType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Security {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String secid;
    private String name;
    private Double profit;

    @Enumerated(EnumType.STRING)
    private SecurityType type;

    @Enumerated(EnumType.STRING)
    private Exchange exchange;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;

    @OneToMany(mappedBy = "security")
    private List<PurchaseInfo> purchaseInfos;

    public SecurityResponseDTO toDto() {
        SecurityResponseDTO responseDTO = new SecurityResponseDTO();
        List<PurchaseInfoDTO> purchaseInfosId = new ArrayList<>();

        responseDTO.setId(id);
        responseDTO.setSecid(secid);
        responseDTO.setName(name);
        responseDTO.setProfit(profit);
        responseDTO.setType(type);
        responseDTO.setExchange(exchange);
        purchaseInfos.forEach(info -> purchaseInfosId.add(info.toDTO()));
        responseDTO.setPurchaseInfos(purchaseInfosId);

        return responseDTO;
    }
}
