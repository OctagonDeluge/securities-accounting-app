package ru.tink.practice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tink.practice.entity.Security;

import java.util.List;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Integer> {

    List<Security> findAllByPortfolioId(Integer portfolio_id);
}
