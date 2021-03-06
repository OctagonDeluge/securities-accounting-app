package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.dto.external.moex.SecurityDTO;
import ru.tink.practice.entity.Security;
import ru.tink.practice.enums.SecurityType;
import ru.tink.practice.exception.SecurityNotFoundException;
import ru.tink.practice.repository.PortfolioRepository;
import ru.tink.practice.repository.SecurityRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SecurityService {

    private final SecurityRepository securityRepository;
    private final PortfolioRepository portfolioRepository;

    public void saveSecurity(Integer portfolioId, SecurityDTO securityDTO, Integer quantity, Double purchasePrice) {
        securityRepository.save(
                new Security(securityDTO.getSecid(),
                        securityDTO.getName(),
                        quantity,
                        purchasePrice,
                        SecurityType.of(securityDTO.getGroup()),
                        portfolioRepository.getReferenceById(portfolioId)
                ));
    }

    public List<Security> getSecurities(Integer portfolioId) {
        return securityRepository.findAllByPortfolioId(portfolioId);
    }

    public Security getSecurity(Integer id) {
        return securityRepository.findById(id)
                .orElseThrow(() -> new SecurityNotFoundException(id));
    }
}
