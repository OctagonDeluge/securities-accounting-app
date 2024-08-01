package ru.tink.practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.tink.practice.enumeration.Currency;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "purchase_info")
public class PurchaseInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private Integer quantity;

    @JsonIgnore
    @ManyToOne
    private Security security;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User client;

    public PurchaseInfo(BigDecimal price, Integer quantity, Security security, User client) {
        this.price = price;
        this.quantity = quantity;
        this.security = security;
        this.client = client;
    }
}
