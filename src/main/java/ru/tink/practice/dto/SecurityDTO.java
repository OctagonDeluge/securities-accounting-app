package ru.tink.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SecurityDTO {
    private String secid;
    private String name;
    private Integer quantity;
    private Double purchase_price;
    private String type;
    private String exchange;
    private Long portfolioId;
}
