package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.dto.SecurityDTO;
import ru.tink.practice.dto.response.SecurityResponseDTO;
import ru.tink.practice.dto.response.StatisticDTO;
import ru.tink.practice.entity.Security;
import ru.tink.practice.enumeration.Exchange;
import ru.tink.practice.enumeration.SecurityType;
import ru.tink.practice.exception.SecurityNotFoundException;
import ru.tink.practice.repository.PortfolioRepository;
import ru.tink.practice.repository.SecurityRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SecurityService {

    private final SecurityRepository securityRepository;
    private final PortfolioRepository portfolioRepository;
    private final PurchaseInfoService purchaseInfoService;

    public void saveSecurity(SecurityDTO securityDTO) {
        Security security =
                securityRepository.findBySecid(securityDTO.getSecid());
        if (security == null) {
            security = new Security();
            security.setSecid(securityDTO.getSecid());
            security.setName(securityDTO.getName());
            security.setProfit(0.0);
            security.setExchange(Exchange.of(securityDTO.getExchange()));
            security.setType(SecurityType.of(securityDTO.getType()));
            security.setPortfolio(portfolioRepository
                    .getReferenceById(securityDTO.getPortfolioId()));
            securityRepository.save(security);
        }
        // calculate profit on new purchase
        purchaseInfoService.savePurchaseInfo(LocalDateTime.now(),
                securityDTO.getPurchasePrice(),
                securityDTO.getQuantity(),
                security);
    }

    public List<SecurityResponseDTO> getSecurities(Long portfolioId) {
        List<SecurityResponseDTO> securities = new ArrayList<>();
        securityRepository.findAllByPortfolioId(portfolioId)
                .forEach(security -> {
                    securities.add(security.toDto());
                });
        return securities;
    }

    public List<StatisticDTO> getNumberOfSecuritiesOfEachType(Long portfolioId) {
        return securityRepository.countBySecurityType(portfolioId);
    }

    public SecurityResponseDTO getSecurity(Long id) {
        return securityRepository.findById(id)
                .orElseThrow(() -> new SecurityNotFoundException(id))
                .toDto();
    }
}
