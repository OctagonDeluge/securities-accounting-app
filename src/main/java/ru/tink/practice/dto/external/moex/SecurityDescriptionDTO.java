package ru.tink.practice.dto.external.moex;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityDescriptionDTO {
    @JsonProperty("description")
    private List<Map<String, Object>> descriptions;
}
