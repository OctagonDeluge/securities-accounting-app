package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.SecurityDTO;
import ru.tink.practice.entity.Security;
import ru.tink.practice.service.SecurityService;

import java.util.List;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @GetMapping("/portfolio/{portfolioId}")
    public List<Security> getSecurities(@PathVariable Long portfolioId) {
        return securityService.getSecurities(portfolioId);
    }

    @GetMapping("/{id}")
    public Security getSecurity(@PathVariable Long id) {
        return securityService.getSecurity(id);
    }

    @PostMapping
    public void addSecurity(@RequestBody SecurityDTO security) {
        securityService.saveSecurity(security);
    }
}
