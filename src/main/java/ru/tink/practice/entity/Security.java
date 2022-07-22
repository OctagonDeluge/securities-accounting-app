package ru.tink.practice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tink.practice.entity.Payment;
import ru.tink.practice.entity.Portfolio;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Security {
    @Id
    private Integer id;
    private String secid;
    private String name;
    private Integer quantity;
    private Double purchase_price;
    private String type;

    @OneToMany(mappedBy = "security", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    @ManyToOne(fetch = FetchType.LAZY)
    private Portfolio portfolio;

    public Security(String secid, String name, Integer quantity, Double purchase_price, String type, Portfolio portfolio) {
        this.secid = secid;
        this.name = name;
        this.quantity = quantity;
        this.purchase_price = purchase_price;
        this.type = type;
        this.portfolio = portfolio;
    }
}
