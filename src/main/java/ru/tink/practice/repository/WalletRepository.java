package ru.tink.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tink.practice.entity.Wallet;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByIdAndClientId(Long walletId, Long clientId);

    Optional<Wallet> findByClientId(Long client);
    void deleteByIdAndClientId(Long id, Long clientId);
}
