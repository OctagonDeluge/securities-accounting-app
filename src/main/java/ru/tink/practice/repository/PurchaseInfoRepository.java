package ru.tink.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tink.practice.entity.PurchaseInfo;

public interface PurchaseInfoRepository extends JpaRepository<PurchaseInfo, Long> {
}
