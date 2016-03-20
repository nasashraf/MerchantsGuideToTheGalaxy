package com.translator.domain.model.numeral;

import com.translator.domain.model.calculator.Credits;

import java.util.List;

import static com.translator.domain.model.calculator.Credits.credits;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public enum RomanNumeral implements Cost {

    M(credits(1000.0)),
    D(credits(500.0)),
    C(credits(100.0), canBeSubtractedFrom(M,D)),
    L(credits(50.0)),
    X(credits(10.0), canBeSubtractedFrom(C,L)),
    V(credits(5.0)),
    I(credits(1.0), canBeSubtractedFrom(X,V));

    private Credits value;
    private List<RomanNumeral> canSubtract;

    RomanNumeral(Credits value) {
        this.value = value;
        this.canSubtract = emptyList();
    }

    RomanNumeral(Credits value, List<RomanNumeral> canSubtract) {
        this.value = value;
        this.canSubtract = canSubtract;
    }

    public Credits value() {
        return value;
    }

    public Credits operation(Credits number) {
        return value().plus(number);
    }

    public Cost next(Cost nextElement) {
        Cost next = nextElement;

        for(Cost subtractableNumeral : canSubtract) {
            if(subtractableNumeral.equals(nextElement)) {
                next = formula(subtractableNumeral);
            }
        }

        return next;
    }

    public boolean greaterThanOrEqualTo(RomanNumeral anotherNumeral) {
        return this.value.greaterThanOrEqualTo(anotherNumeral.value);
    }

    private static List<RomanNumeral> canBeSubtractedFrom(RomanNumeral... numerals) {
        return asList(numerals);
    }

    private Cost formula(final Cost numeral) {
        final Credits val = value();

        return new Cost() {
            public Credits value() {
                return numeral.value().minus(val.multipliedByTwo());
            }

            public Credits operation(Credits number) {
                return value().plus(number);
            }

            public Cost next(Cost nextElement) {
                return nextElement;
            }
        };
    }
}
