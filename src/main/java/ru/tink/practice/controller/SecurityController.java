package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.response.SecurityResponseDTO;
import ru.tink.practice.entity.Security;
import ru.tink.practice.security.SecurityUser;
import ru.tink.practice.service.SecurityService;

import java.util.List;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;
    private final Integer PAGE_SIZE = 10;

    /*@GetMapping("/portfolio/{id}")
    public List<SecurityResponseDTO> getSecurities(Authentication authentication,
                                                   @PathVariable Long id, @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return securityService
                .getSecuritiesDTO((SecurityUser) authentication.getPrincipal(), id);
    }*/

    @GetMapping("/portfolio/{id}")
    public List<SecurityResponseDTO> getSecurities(Authentication authentication,
                                                   @PathVariable Long id) {
        return securityService
                .getSecuritiesDTO((SecurityUser) authentication.getPrincipal(), id);
    }

    @GetMapping("/{id}")
    public SecurityResponseDTO getSecurity(Authentication authentication, @PathVariable Long id) {
        Security security = securityService
                .getSecurity((SecurityUser) authentication.getPrincipal(), id);
        return new SecurityResponseDTO(security);
    }
}
