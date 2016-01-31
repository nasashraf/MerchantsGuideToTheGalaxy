package com.translator.domain.model.numeral;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public enum RomanNumeralNew implements Cost {

    M(1000.0),
    D(500.0),
    C(100.0, canBeSubtractedFrom(M,D)),
    L(50.0),
    X(10.0, canBeSubtractedFrom(C,L)),
    V(5.0),
    I(1.0, canBeSubtractedFrom(X,V));

    private Double value;
    private List<? extends Cost> canSubtract;

    RomanNumeralNew(Double value) {
        this.value = value;
        this.canSubtract = emptyList();
    }

    RomanNumeralNew(Double value, List<? extends Cost> canSubtract) {
        this.value = value;
        this.canSubtract = canSubtract;
    }

    public Double value() {
        return value;
    }

    public Double operation(Double number) {
        return value() + number;
    }

    public Cost next(Cost nextElement) {
        Cost next = nextElement;

        if (canSubtract.contains(nextElement)) {
            next = formula(nextElement);
        }

        return next;
    }

    private static List<? extends Cost> canBeSubtractedFrom(RomanNumeralNew... numerals) {
        return asList(numerals);
    }
    private Cost formula(final Cost numeral) {
        final Double val = value();

        return new Cost() {
            public Double value() {
                return (numeral.value() - (2 * val));
            }

            public Double operation(Double number) {
                return value() + number;
            }

            public Cost next(Cost nextElement) {
                return nextElement;
            }

        };
    }

    public boolean greaterThanOrEqualTo(RomanNumeralNew anotherNumeral) {
        return this.value >= anotherNumeral.value;
    }
}
