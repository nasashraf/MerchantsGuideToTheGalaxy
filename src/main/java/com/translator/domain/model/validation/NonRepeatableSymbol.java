package com.translator.domain.model.validation;

import com.translator.domain.model.numeral.RomanNumeral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.translator.domain.model.numeral.RomanNumeral.*;
import static java.util.Arrays.asList;

public class NonRepeatableSymbol implements Validator {

    private static final int EXISTS = -1;
    private static final boolean CONTAINS_INVALID_SEQUENCE = false;
    private static final boolean DOES_NOT_CONTAIN_INVALID_SEQUENCE = true;

    private static final List<List<RomanNumeral>> SYMBOLS_NOT_REPEATABLE = new ArrayList<List<RomanNumeral>>() {{
        add(asList(V,V));
        add(asList(L,L));
        add(asList(D,D));
    }};

    public boolean validate(List<RomanNumeral> romanNumerals) {
        return numeralsDoNotContainNonRepeatableSymbols(romanNumerals);

    }

    private boolean numeralsDoNotContainNonRepeatableSymbols(List<RomanNumeral> numerals) {
        return checkNumeralsDoNotContainInvalidSequences(numerals, SYMBOLS_NOT_REPEATABLE);
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

