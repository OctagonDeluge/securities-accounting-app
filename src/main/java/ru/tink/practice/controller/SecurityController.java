package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tink.practice.entity.Security;
import ru.tink.practice.service.SecurityService;

import java.util.List;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @GetMapping("/portfolio/{portfolioId}")
    public List<Security> getSecurities(@PathVariable Integer portfolioId) {
        return securityService.getSecurities(portfolioId);
    }

    @GetMapping("/{id}")
    public Security getSecurity(@PathVariable Integer id) {
        return null;
    }


}
