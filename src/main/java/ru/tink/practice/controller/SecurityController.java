package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.SecurityDTO;
import ru.tink.practice.dto.response.SecurityResponseDTO;
import ru.tink.practice.service.SecurityService;

import java.util.List;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @GetMapping("/portfolio/{id}")
    public List<SecurityResponseDTO> getSecurities(@PathVariable Long id) {
        return securityService.getSecuritiesDTO(id);
    }

    @GetMapping("/{id}")
    public SecurityResponseDTO getSecurity(@PathVariable Long id) {
        return securityService.getSecurity(id);
    }

    @PostMapping
    public SecurityResponseDTO addSecurity(@RequestBody SecurityDTO security) {
        return securityService.saveSecurity(security);
    }

    @DeleteMapping("/{id}")
    public void deleteSecurity(@PathVariable Long id) {
        securityService.deleteSecurity(id);
    }
}
