package ru.tink.practice.enumeration.external.moex;

public enum SecurityDTOType {
    SHARE("stock_shares"),
    BOND("stock_bonds");

    private final String type;

    SecurityDTOType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
