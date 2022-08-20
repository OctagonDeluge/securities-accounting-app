const map = {
    stock_shares: "Акция",
    stock_bonds: "Облигация",
}

const values = {
    BOND:"Облигации",
    SHARE:"Акции"
}

export function TypeConverterForSecurity(type) {
    return map[type] ?? null;
}

export function TypeConverterForStatistics(type) {
    return values[type] ?? null;
}