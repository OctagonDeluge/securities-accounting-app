package ru.tink.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tink.practice.entity.Portfolio;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findAllByClientId(Long clientId);

    Optional<Portfolio> findByIdAndClientId(Long id, Long clientId);
}
