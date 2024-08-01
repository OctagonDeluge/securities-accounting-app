package ru.tink.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tink.practice.dto.request.CreateWalletRequest;
import ru.tink.practice.entity.User;
import ru.tink.practice.entity.Wallet;
import ru.tink.practice.exception.EntityNotFoundException;
import ru.tink.practice.repository.WalletRepository;
import ru.tink.practice.security.SecurityUser;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public Wallet getWalletByClientId(Long clietnId) {
        return walletRepository.findByClientId(clietnId)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));
    }

    public Wallet getWalletByIdAndClientId(Long id, Long clientId) {
        return walletRepository.findByIdAndClientId(id, clientId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Wallet with %d not found", id)));
    }

}
