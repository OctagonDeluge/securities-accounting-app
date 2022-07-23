package ru.tink.practice.dto.external.moex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DividendsDTO {
    @JsonProperty("dividends")
    List<DividendDTO> dividends;
}
