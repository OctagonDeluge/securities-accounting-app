package ru.tink.practice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.tink.practice.entity.PurchaseInfo;

import java.util.List;

public interface PurchaseInfoRepository extends PagingAndSortingRepository<PurchaseInfo, Long>, JpaRepository<PurchaseInfo, Long> {

    Page<PurchaseInfo> findAllBySecurityIdAndClientId(Long securityId, Long clientId, Pageable pageable);

    List<PurchaseInfo> findAllBySecurityIdAndClientId(Long securityId, Long clientId);
}
