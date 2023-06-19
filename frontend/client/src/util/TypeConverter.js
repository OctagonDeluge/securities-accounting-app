const map = {
    stock_shares: "Акция",
    stock_bonds: "Облигация",
}

const values = {
    BOND:"Облигации",
    SHARE:"Акции"
}

const levels = {
    EASY:"Легко",
    NORMAL:"Нормально",
    HARD:"Сложно"
}

const operations = {
    PURCHASE: "Покупка",
    SALE: "Продажа",
    COUPON: "Купон",
    DIVIDEND: "Дивиденд"
}

export function TypeConverterForSecurity(type) {
    return map[type] ?? null;
}

export function TypeConverterForStatistics(type) {
    return values[type] ?? null;
}

export function LevelConverter(type) {
    return levels[type] ?? null;
}

export function OperationTypeConverter(type) {
    return operations[type] ?? null;
}

