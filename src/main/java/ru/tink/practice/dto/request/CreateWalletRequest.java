package ru.tink.practice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.tink.practice.enumeration.Currency;

@Getter
@Setter
@AllArgsConstructor
public class CreateWalletRequest {
    private String name;
    private Currency currency;
}
