package ru.tink.practice.enums;

public enum SecurityType {
    SHARE("share"),
    BOND("bond");

    private final String type;

    SecurityType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static SecurityType of(String anotherType) {
        for(SecurityType type:values()) {
            if(anotherType.contains(type.getType())) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("No enum found with type: %s", anotherType));
    }
}
