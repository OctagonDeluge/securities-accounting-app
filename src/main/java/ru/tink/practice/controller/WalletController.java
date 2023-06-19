package ru.tink.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tink.practice.dto.request.CreateWalletRequest;
import ru.tink.practice.security.SecurityUser;
import ru.tink.practice.service.WalletService;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    //TODO: transfer between wallets
    //TODO: transfer between two persons
}
