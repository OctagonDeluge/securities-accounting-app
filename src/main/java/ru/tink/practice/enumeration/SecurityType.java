package ru.tink.practice.enumeration;

public enum SecurityType {
    SHARE("shares"),
    BOND("bonds");

    private final String name;

    SecurityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SecurityType of(String anotherType) {
        for(SecurityType type:values()) {
            if(anotherType.contains(type.getName())) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("No enum found with type: %s", anotherType));
    }
}
