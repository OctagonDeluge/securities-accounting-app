package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tink.practice.service.integration.ExchangeIntegrationService;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final ExchangeIntegrationService exchangeIntegrationService;

    @GetMapping
    public void test() {
        //exchangeIntegrationService.test();
    }
}
