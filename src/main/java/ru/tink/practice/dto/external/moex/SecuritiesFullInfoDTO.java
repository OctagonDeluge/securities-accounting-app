package ru.tink.practice.dto.external.moex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecuritiesFullInfoDTO {
    @JsonProperty("securities")
    private List<SecurityFullInfoDTO> securities;
}
