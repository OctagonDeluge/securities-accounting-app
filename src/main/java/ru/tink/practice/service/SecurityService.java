package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.dto.SecurityDTO;
import ru.tink.practice.dto.response.SecurityResponseDTO;
import ru.tink.practice.dto.response.StatisticDTO;
import ru.tink.practice.entity.PurchaseInfo;
import ru.tink.practice.entity.Security;
import ru.tink.practice.enumeration.Exchange;
import ru.tink.practice.enumeration.SecurityType;
import ru.tink.practice.exception.SecurityNotFoundException;
import ru.tink.practice.repository.PortfolioRepository;
import ru.tink.practice.repository.PurchaseInfoRepository;
import ru.tink.practice.repository.SecurityRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SecurityService {

    private final SecurityRepository securityRepository;
    private final PortfolioRepository portfolioRepository;
    private final PurchaseInfoRepository purchaseInfoRepository;

    public void saveSecurity(SecurityDTO securityDTO) {
        Security security =
                securityRepository.findBySecid(securityDTO.getSecid());
        if (security == null) {
            security = new Security();
            security.setSecid(securityDTO.getSecid());
            security.setName(securityDTO.getName());
            //set current price
            security.setProfit(BigDecimal.valueOf(0));
            security.setExchange(Exchange.of(securityDTO.getExchange()));
            security.setType(SecurityType.of(securityDTO.getType()));
            security.setPortfolio(portfolioRepository
                    .getReferenceById(securityDTO.getPortfolioId()));
            security.setCurrency(securityDTO.getCurrency());
            securityRepository.save(security);
        }
        // calculate profit on new purchase
        PurchaseInfo purchaseInfo = new PurchaseInfo();
        purchaseInfo.setPurchaseDate(LocalDateTime.now());
        purchaseInfo.setPrice(securityDTO.getPurchasePrice());
        purchaseInfo.setQuantity(securityDTO.getQuantity());
        purchaseInfo.setCurrency(securityDTO.getCurrency());
        purchaseInfo.setSecurity(security);
        purchaseInfoRepository.save(purchaseInfo);
    }

    public void deleteSecurity(Long id) {
        securityRepository.deleteById(id);
    }

    public List<SecurityResponseDTO> getSecurities(Long portfolioId) {
        List<SecurityResponseDTO> securities = new ArrayList<>();
        securityRepository.findAllByPortfolioId(portfolioId)
                .forEach(security -> securities.add(new SecurityResponseDTO(security)));
        return securities;
    }

    public List<StatisticDTO> getNumberOfSecuritiesOfEachType(Long portfolioId) {
        return securityRepository.countBySecurityType(portfolioId);
    }

    public SecurityResponseDTO getSecurity(Long id) {
        return new SecurityResponseDTO(securityRepository.findById(id)
                .orElseThrow(() -> new SecurityNotFoundException(id)));
    }
}
