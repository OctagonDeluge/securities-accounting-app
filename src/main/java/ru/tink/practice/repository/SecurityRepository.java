package ru.tink.practice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tink.practice.dto.response.StatisticDTO;
import ru.tink.practice.entity.Security;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {

    Optional<Security> findByIdAndClientId(Long id, Long clientId);

    List<Security> findAllByPortfolioIdAndClientId(Long portfolioId, Long clientId);

    Security findByPortfolioIdAndSecid(Long portfolioId, String secid);

    @Query(value = "select type, count(1) from " +
            "(select type from security where portfolio_id = ?1 and client_id = ?2) as st\n" +
            "group by type", nativeQuery = true)
    List<StatisticDTO> countBySecurityType(Long portfolioId, Long clientId);
}
