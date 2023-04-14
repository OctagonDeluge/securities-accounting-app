package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.SecurityDTO;
import ru.tink.practice.dto.response.SecurityResponseDTO;
import ru.tink.practice.security.SecurityUser;
import ru.tink.practice.service.SecurityService;

import java.util.List;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @GetMapping("/portfolio/{id}")
    public List<SecurityResponseDTO> getSecurities(Authentication authentication, @PathVariable Long id) {
        return securityService.getSecuritiesDTO((SecurityUser) authentication.getPrincipal(), id);
    }

    @GetMapping("/{id}")
    public SecurityResponseDTO getSecurity(Authentication authentication, @PathVariable Long id) {
        return securityService.getSecurity((SecurityUser) authentication.getPrincipal(), id);
    }

    @PostMapping
    public SecurityResponseDTO addSecurity(Authentication authentication, @RequestBody SecurityDTO security) {
        return securityService.saveSecurity((SecurityUser) authentication.getPrincipal(), security);
    }

    @DeleteMapping("/{id}")
    public void deleteSecurity(Authentication authentication, @PathVariable Long id) {
        securityService.deleteSecurity((SecurityUser) authentication.getPrincipal(),id);
    }
}
