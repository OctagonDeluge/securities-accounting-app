package ru.tink.practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tink.practice.enumeration.Currency;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "purchase_info")
public class PurchaseInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime purchaseDate;
    private BigDecimal price;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @JsonIgnore
    @ManyToOne
    private Security security;
}