package ru.tink.practice.dto.external.moex;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityDescriptionsDTO {
    @JsonProperty("description")
    private List<Map<String, Object>> descriptions;
}
