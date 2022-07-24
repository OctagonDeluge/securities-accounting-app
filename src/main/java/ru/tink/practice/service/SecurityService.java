package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.dto.SecurityDTO;
import ru.tink.practice.entity.Security;
import ru.tink.practice.enums.Exchange;
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

    public void saveSecurity(SecurityDTO securityDTO) {
        Security security = new Security();
        security.setSecid(securityDTO.getSecid());
        security.setName(securityDTO.getName());
        security.setQuantity(securityDTO.getQuantity());
        security.setPurchase_price(securityDTO.getPurchase_price());
        security.setExchange(Exchange.valueOf(securityDTO.getExchange().toUpperCase()));
        security.setType(SecurityType.of(securityDTO.getType()));
        security.setPortfolio(portfolioRepository
                .getReferenceById(securityDTO.getPortfolioId()));

        securityRepository.save(security);
    }

    public List<Security> getSecurities(Long portfolioId) {
        return securityRepository.findAllByPortfolioId(portfolioId);
    }

    public Security getSecurity(Long id) {
        return securityRepository.findById(id)
                .orElseThrow(() -> new SecurityNotFoundException(id));
    }
}
