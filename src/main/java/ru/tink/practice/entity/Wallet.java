package ru.tink.practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.tink.practice.enumeration.Currency;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private User client;

    @JsonIgnore
    @OneToMany(mappedBy = "wallet")
    private List<OperationInfo> operationInfos;

    public Wallet(String name, BigDecimal balance, Currency currency, User client) {
        this.name = name;
        this.balance = balance;
        this.currency = currency;
        this.client = client;
    }
}
