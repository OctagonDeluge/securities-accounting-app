package ru.tink.practice.dto.external.moex;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tink.practice.enumeration.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentPriceDTO {
    private BigDecimal close;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
    private Currency currency;
    public void fixCloseForBond() {
        close = close.multiply(BigDecimal.valueOf(10));
    }
}
