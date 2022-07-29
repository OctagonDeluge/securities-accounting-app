package ru.tink.practice.enumeration;

public enum Exchange {
    MOEX("moex");

    private final String name;

    Exchange(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Exchange of(String anotherExchange) {
        for(Exchange exchange:values()) {
            if(anotherExchange.contains(exchange.getName())) {
                return exchange;
            }
        }
        throw new IllegalArgumentException(String.format("No enum found with type: %s", anotherExchange));
    }
}
