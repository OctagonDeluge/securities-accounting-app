package ru.tink.practice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
public class Payment {
    @Id
    private Integer id;
    private String name;
    private Date paymentDate;
    private Double value;

    @ManyToOne(fetch = FetchType.LAZY)
    private Security security;
}
