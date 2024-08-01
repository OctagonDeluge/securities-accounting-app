package ru.tink.practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.tink.practice.enumeration.Currency;
import ru.tink.practice.enumeration.Exchange;
import ru.tink.practice.enumeration.SecurityType;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal totalCost;
    private BigDecimal profit;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private SecurityType type;

    @Enumerated(EnumType.STRING)
    private Exchange exchange;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;
    
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "security", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseInfo> purchaseInfos;

    @ManyToOne(fetch = FetchType.LAZY)
    private User client;

    public Security(String secid, String name, BigDecimal totalCost, BigDecimal profit,
                    Integer quantity, SecurityType type, Exchange exchange,
                    Currency currency, Portfolio portfolio, User client) {
        this.secid = secid;
        this.name = name;
        this.totalCost = totalCost;
        this.profit = profit;
        this.quantity = quantity;
        this.type = type;
        this.exchange = exchange;
        this.currency = currency;
        this.portfolio = portfolio;
        this.client = client;
    }
}
