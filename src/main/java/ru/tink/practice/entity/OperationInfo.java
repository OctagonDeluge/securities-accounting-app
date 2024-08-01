package ru.tink.practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tink.practice.entity.enumeration.OperationType;
import ru.tink.practice.enumeration.Currency;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OperationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    private String secid;
    private String name;
    private Integer quantity;
    private BigDecimal operationCost;
    private BigDecimal overall;
    private BigDecimal profit;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private LocalDateTime operationDate;

    @JsonIgnore
    @ManyToOne
    private Wallet wallet;

    @JsonIgnore
    @ManyToOne
    private User client;

    public OperationInfo(OperationType operationType, String secid, String name,
                         Integer quantity, BigDecimal operationCost, BigDecimal overall,
                         BigDecimal profit, Currency currency, LocalDateTime operationDate) {
        this.operationType = operationType;
        this.secid = secid;
        this.name = name;
        this.quantity = quantity;
        this.operationCost = operationCost;
        this.overall = overall;
        this.profit = profit;
        this.currency = currency;
        this.operationDate = operationDate;
    }

    public OperationInfo(OperationType operationType, String secid, String name, Integer quantity,
                         BigDecimal operationCost, BigDecimal overall, BigDecimal profit, Currency currency,
                         LocalDateTime operationDate, Wallet wallet, User client) {
        this.operationType = operationType;
        this.secid = secid;
        this.name = name;
        this.quantity = quantity;
        this.operationCost = operationCost;
        this.overall = overall;
        this.profit = profit;
        this.currency = currency;
        this.operationDate = operationDate;
        this.wallet = wallet;
        this.client = client;
    }
}
