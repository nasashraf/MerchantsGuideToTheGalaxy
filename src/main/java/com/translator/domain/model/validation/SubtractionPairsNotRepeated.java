package com.translator.domain.model.validation;

import com.translator.domain.model.numeral.RomanNumeral;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.translator.domain.model.numeral.RomanNumeral.*;
import static com.translator.domain.model.numeral.RomanNumeral.C;
import static com.translator.domain.model.numeral.RomanNumeral.M;
import static java.util.Arrays.asList;

public class SubtractionPairsNotRepeated implements Validator {

    private static final HashMap<List<RomanNumeral>, Integer> SUBTRACTION_NUMERAL_VALUES = new HashMap<List<RomanNumeral>, Integer>(){{
        put(asList(I,V), 4);
        put(asList(I,X), 9);
        put(asList(X,L), 40);
        put(asList(X,C), 90);
        put(asList(C,D), 400);
        put(asList(C,M), 900);
    }};

    public boolean validate(List<RomanNumeral> romanNumerals) {
        return subtractionPairsNotRepeated(romanNumerals);
    }

    private boolean subtractionPairsNotRepeated(List<RomanNumeral> numerals) {
        for (List<RomanNumeral> invalidSequence : SUBTRACTION_NUMERAL_VALUES.keySet()) {
            int first = Collections.indexOfSubList(numerals, invalidSequence);
            int second = Collections.lastIndexOfSubList(numerals, invalidSequence);

            if (first != second) {
                return false;
            }
        }

        return true;
    }

}
