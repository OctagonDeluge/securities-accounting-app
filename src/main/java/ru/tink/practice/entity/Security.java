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

    @Enumerated(EnumType.STRING)
    private SecurityType type;

    @Enumerated(EnumType.STRING)
    private Exchange exchange;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;

    @OneToMany(mappedBy = "security", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseInfo> purchaseInfos;
}
