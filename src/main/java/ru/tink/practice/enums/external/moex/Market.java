package ru.tink.practice.enums.external.moex;

public enum Market {
    BONDS("bonds"),
    SHARES("shares");

    private final String name;

    Market(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String of(String anotherType) {
        for(Market market:values()) {
            if(anotherType.contains(market.getName())) {
                return market.getName();
            }
        }
        throw new IllegalArgumentException(String.format("No enum found with type: %s", anotherType));
    }
}
