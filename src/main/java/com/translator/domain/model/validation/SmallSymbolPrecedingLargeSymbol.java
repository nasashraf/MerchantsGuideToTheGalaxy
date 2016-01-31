package com.translator.domain.model.validation;

import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;

public class SmallSymbolPrecedingLargeSymbol implements Validator {
    private static final int AT_FIRST_POSITION = 0;
    private static final int AT_SECOND_POSITION = 1;
    private static final int AT_THIRD_POSITION = 2;


    public boolean validate(List<RomanNumeral> romanNumerals) {
        return noMoreThanOneSmallValueSymbolPrecedingLargeSymbol(true, romanNumerals);
    }

    private boolean noMoreThanOneSmallValueSymbolPrecedingLargeSymbol(Boolean precedingValid, List<RomanNumeral> romanNumerals) {
        if (precedingValid == false) return false;
        if (romanNumerals.size() <= 2) return true;

        boolean result = true;
        if (romanNumerals.get(AT_FIRST_POSITION).value() + romanNumerals.get(AT_SECOND_POSITION).value() < romanNumerals.get(AT_THIRD_POSITION).value()) {
            result = false;
        }

        return noMoreThanOneSmallValueSymbolPrecedingLargeSymbol(result, tailFrom(1, romanNumerals));
    }

    private List<RomanNumeral> tailFrom(int index, List<RomanNumeral> romanNumeralNumber) {
        return romanNumeralNumber.subList(index, romanNumeralNumber.size());
    }
}
