package ru.tink.practice.service.external.exchange.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tink.practice.dto.SecurityDTO;
import ru.tink.practice.service.external.exchange.ExternalExchangeService;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MoexExternalExchangeService implements ExternalExchangeService {

    private final RestTemplate restTemplate;

    @Value("${issmoex.api.url}")
    private String url;

    @Value("${issmoex.api.serviceName}")
    private String serviceName;

    @Override
    public List<SecurityDTO> getSecuritiesByName(String securityName) {
        URI destUrl = UriComponentsBuilder.fromHttpUrl(url)
                .pathSegment("securities.json")
                .queryParam("iss.meta", "off")
                .queryParam("iss.json", "extended")
                .queryParam("securities.columns", "secid, shortname, name")
                .queryParam("q", securityName).build().toUri();

        ResponseEntity<String> securitiesJson = restTemplate.getForEntity(destUrl, String.class);

        List<SecurityDTO> securities = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(securitiesJson.getBody());
            securities = mapper.readValue(root.get(1)
                    .get("securities")
                    .toString(), new TypeReference<>() {});
            securities.forEach(security -> security.setExchangeName(serviceName));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return securities;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }
}
