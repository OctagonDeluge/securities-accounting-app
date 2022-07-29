package ru.tink.practice.dto.response;

import ru.tink.practice.enumeration.SecurityType;

public interface StatisticDTO {

    SecurityType getType();

    Integer getCount();
}
