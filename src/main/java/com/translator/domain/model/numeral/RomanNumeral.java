package com.translator.domain.model.numeral;

public enum RomanNumeral {
    I(1),
    V(5),
    X(10),
    L(50),
    C(100),
    D(500),
    M(1000);

    private Integer decimalValue;

    RomanNumeral(Integer decimalValue) {
        this.decimalValue = decimalValue;
    }

    public Integer decimalValue() {
        return decimalValue;
    }

    public boolean greaterThanOrEqualTo(RomanNumeral anotherNumeral) {
        return this.decimalValue >= anotherNumeral.decimalValue;
    }
}
