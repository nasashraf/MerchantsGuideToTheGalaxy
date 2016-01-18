package com.translator.domain.model.validation;

import com.translator.domain.model.numeral.RomanNumeral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.translator.domain.model.numeral.RomanNumeral.*;
import static com.translator.domain.model.numeral.RomanNumeral.M;
import static java.util.Arrays.asList;

public class ThreeSymbolsInSuccession implements Validator {

    private static final int EXISTS = -1;
    private static final boolean CONTAINS_INVALID_SEQUENCE = false;
    private static final boolean DOES_NOT_CONTAIN_INVALID_SEQUENCE = true;

    private static final List<List<RomanNumeral>> SYMBOLS_NOT_REPEATABLE_MORE_THAN_THREE = new ArrayList<List<RomanNumeral>>() {{
        add(asList(I,I,I,I));
        add(asList(X,X,X,X));
        add(asList(C,C,C,C));
        add(asList(M,M,M,M));
    }};

    public boolean validate(List<RomanNumeral> romanNumerals) {
        return repeatableSymbolsDoNotAppearMoreThanThreeTimesInSuccession(romanNumerals);
    }

    private boolean repeatableSymbolsDoNotAppearMoreThanThreeTimesInSuccession(List<RomanNumeral> numerals) {
        return checkNumeralsDoNotContainInvalidSequences(numerals, SYMBOLS_NOT_REPEATABLE_MORE_THAN_THREE);
    }

    private boolean checkNumeralsDoNotContainInvalidSequences(List<RomanNumeral> numerals, List<List<RomanNumeral>> invalidSequences) {
        for (List<RomanNumeral> invalidSequence : invalidSequences) {
            if (Collections.indexOfSubList(numerals, invalidSequence) != EXISTS) {
                return CONTAINS_INVALID_SEQUENCE;
            }
        }

        return DOES_NOT_CONTAIN_INVALID_SEQUENCE;
    }
}
