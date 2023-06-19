package ru.tink.practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Security> securities;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User client;

    public Portfolio(String name, BigDecimal totalCost, User user) {
        this.name = name;
        this.totalCost = totalCost;
        this.client = user;
    }
}
