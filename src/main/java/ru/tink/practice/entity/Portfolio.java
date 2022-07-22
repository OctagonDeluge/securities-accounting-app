package ru.tink.practice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Portfolio {
    @Id
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "portfolio")
    private List<Security> securities;

    public Portfolio(String name) {
        this.name = name;
    }
}
