package ru.tink.practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Portfolio {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal totalCost;
    private BigDecimal profit;

    @JsonIgnore
    @OneToMany(mappedBy = "portfolio")
    private List<Security> securities;

    public Portfolio(String name, BigDecimal totalCost, BigDecimal profit) {
        this.name = name;
        this.profit = profit;
        this.totalCost = totalCost;
    }
}
