package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.tink.practice.dto.response.SecurityResponseDTO;
import ru.tink.practice.dto.response.StatisticDTO;
import ru.tink.practice.entity.Security;
import ru.tink.practice.exception.SecurityNotFoundException;
import ru.tink.practice.repository.SecurityRepository;
import ru.tink.practice.security.SecurityUser;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Validated
@Service
public class SecurityService {

    private final SecurityRepository securityRepository;

    public Security saveSecurity(Security security) {
        return securityRepository.save(security);
    }

    public void deleteSecurity(SecurityUser securityUser, Long id) {
        Security security = securityRepository
                .findByIdAndClientId(id, securityUser.getId())
                .orElseThrow(() -> new SecurityNotFoundException(id));
        securityRepository.delete(security);
    }

    public List<SecurityResponseDTO> getSecuritiesDTO(SecurityUser securityUser, Long portfolioId) {
        List<SecurityResponseDTO> securities = new ArrayList<>();
        securityRepository
                .findAllByPortfolioIdAndClientId(portfolioId, securityUser.getId())
                .forEach(security -> securities.add(new SecurityResponseDTO(security)));
        return securities;
    }

    public List<StatisticDTO> getNumberOfSecuritiesOfEachType(SecurityUser securityUser, Long portfolioId) {
        return securityRepository.countBySecurityType(portfolioId, securityUser.getId());
    }

    public Security getSecurityByPortfolioId(Long portfolioId, String secid) {
        return securityRepository.findByPortfolioIdAndSecid(portfolioId, secid);
    }

    public Security getSecurity(SecurityUser securityUser, Long id) {
        return securityRepository.findByIdAndClientId(id, securityUser.getId())
                .orElseThrow(() -> new SecurityNotFoundException(id));
    }

    public Security getSecurity(Long id) {
        return securityRepository.findById(id)
                .orElseThrow(() -> new SecurityNotFoundException(id));
    }
}
