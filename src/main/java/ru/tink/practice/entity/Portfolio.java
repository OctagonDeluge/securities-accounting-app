package ru.tink.practice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Portfolio {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double currentPrice;
    private Double profit;

    @JsonIgnore
    @OneToMany(mappedBy = "portfolio")
    private List<Security> securities;

    public Portfolio(String name, Double profit) {
        this.name = name;
        this.profit = profit;
    }
}
