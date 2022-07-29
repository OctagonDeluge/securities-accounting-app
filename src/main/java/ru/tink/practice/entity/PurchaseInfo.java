package ru.tink.practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tink.practice.dto.response.PurchaseInfoDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "purchase_info")
public class PurchaseInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;
    LocalDateTime purchaseDate;
    Double price;
    Integer quantity;

    @JsonIgnore
    @ManyToOne
    private Security security;

    public PurchaseInfoDTO toDTO() {
        PurchaseInfoDTO purchaseInfo = new PurchaseInfoDTO();
        purchaseInfo.setId(id);
        return purchaseInfo;
    }
}
