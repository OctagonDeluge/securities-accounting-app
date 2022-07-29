package ru.tink.practice.enumeration.external.moex;

public enum Engine {
    STOCK("stock");

    private final String name;

    Engine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
