package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tink.practice.dto.external.moex.SecuritiesShortInfoDTO;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Slf4j
public class TestController {

    private final RestTemplate restTemplate;

    @GetMapping
    public String test() {
        URI destUrl = UriComponentsBuilder.fromHttpUrl("https://www.alphavantage.co")
                .pathSegment("/query")
                .queryParam("function", "OVERVIEW")
                .queryParam("symbol", "IBM")
                .queryParam("apikey", "Q9GGE2JFISLWXXWO")
                .build().toUri();
        return restTemplate.getForEntity(destUrl, String.class).getBody();
    }

    @GetMapping("/moex")
    public String testMoex() {
        URI destUrl = UriComponentsBuilder.fromHttpUrl("https://iss.moex.com/iss")
                .pathSegment("securities")
                .pathSegment("SBER").pathSegment(".json")
                .queryParam("iss.meta", "off")
                .queryParam("iss.json", "extended")
                .queryParam("iss.only", "description")
                .queryParam("description.columns", "name,value")
                .build().toUri();
        return restTemplate.getForObject(destUrl, String.class);
    }
}
