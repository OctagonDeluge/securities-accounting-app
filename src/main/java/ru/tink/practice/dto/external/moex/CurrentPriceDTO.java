package ru.tink.practice.dto.external.moex;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentPriceDTO {
    private Double close;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;

    public void fixCloseForBond() {
        close *= 10;
    }
}
