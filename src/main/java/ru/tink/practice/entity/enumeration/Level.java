package ru.tink.practice.entity.enumeration;

public enum Level {
    EASY(500_000),
    NORMAL(60_000),
    HARD(20_000),
    INVESTOR(2_000_000);

    private final int money;

    Level(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }
}
