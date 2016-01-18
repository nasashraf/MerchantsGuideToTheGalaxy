package com.translator.domain.model.validation;

import com.translator.domain.model.numeral.RomanNumeral;

import java.util.HashMap;
import java.util.List;

import static com.translator.domain.model.numeral.RomanNumeral.*;
import static java.util.Arrays.asList;

public class DescendingOrder implements Validator {

    private static final int AT_FIRST_POSITION = 0;
    private static final int AT_SECOND_POSITION = 1;

    private static final HashMap<List<RomanNumeral>, Integer> SUBTRACTION_NUMERAL_VALUES = new HashMap<List<RomanNumeral>, Integer>(){{
        put(asList(I,V), 4);
        put(asList(I,X), 9);
        put(asList(X,L), 40);
        put(asList(X,C), 90);
        put(asList(C,D), 400);
        put(asList(C,M), 900);
    }};

    public boolean validate(List<RomanNumeral> romanNumerals) {
        return determineNumeralsInDescendingOrder(true, romanNumerals);
    }

    private Boolean determineNumeralsInDescendingOrder(Boolean inOrder, List<RomanNumeral> numerals) {
        if (inOrder == false) return false;
        if (numerals.size() == 1) return true;

        RomanNumeral firstSymbol = getSymbolFrom(numerals, AT_FIRST_POSITION);
        RomanNumeral secondSymbol = getSymbolFrom(numerals, AT_SECOND_POSITION);

        List<RomanNumeral> firstPairOfNumerals = asList(firstSymbol,secondSymbol);

        boolean result;
        if (SUBTRACTION_NUMERAL_VALUES.containsKey(firstPairOfNumerals)) {
            result = true;
        } else {
            result = firstSymbol.greaterThanOrEqualTo(secondSymbol);
        }

        return determineNumeralsInDescendingOrder(result, tailFrom(AT_SECOND_POSITION, numerals));
    }

    private RomanNumeral getSymbolFrom(List<RomanNumeral> romanNumeralNumber, int index) {
        return romanNumeralNumber.get(index);
    }

    private List<RomanNumeral> tailFrom(int index, List<RomanNumeral> romanNumeralNumber) {
        return romanNumeralNumber.subList(index, romanNumeralNumber.size());
    }
}
