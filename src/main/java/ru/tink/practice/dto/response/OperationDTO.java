package ru.tink.practice.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.tink.practice.entity.OperationInfo;
import ru.tink.practice.entity.enumeration.OperationType;
import ru.tink.practice.enumeration.Currency;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class OperationDTO {
    private Long id;
    private OperationType operationType;
    private String secid;
    private String name;
    private Integer quantity;
    private BigDecimal operationCost;
    private BigDecimal overall;
    private BigDecimal profit;
    private Currency currency;
    private String operationDate;

    public OperationDTO(OperationInfo operationInfo) {
        id = operationInfo.getId();
        operationType = operationInfo.getOperationType();
        secid = operationInfo.getSecid();
        name = operationInfo.getName();
        quantity = operationInfo.getQuantity();
        operationCost = operationInfo.getOperationCost();
        overall = operationInfo.getOverall();
        profit = operationInfo.getProfit();
        currency = operationInfo.getCurrency();
        operationDate = operationInfo.getOperationDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
