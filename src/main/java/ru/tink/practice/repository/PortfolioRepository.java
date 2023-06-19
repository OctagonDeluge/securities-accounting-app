package ru.tink.practice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.tink.practice.entity.Portfolio;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends
        PagingAndSortingRepository<Portfolio, Long>, JpaRepository<Portfolio, Long> {

    Page<Portfolio> findAllByClientIdOrderByIdDesc(Long clientId, Pageable pageable);

    Optional<Portfolio> findByIdAndClientId(Long id, Long clientId);
}
