package com.translator.domain.model.validation;

import com.translator.domain.model.numeral.RomanNumeral;

import java.util.ArrayList;
import java.util.List;

public class RomanNumeralValidator implements Validator {

    private List<Validator> romanNumeralValidators;

    public RomanNumeralValidator() {
        this.romanNumeralValidators = new ArrayList<Validator>() {{
           add(new DescendingOrder());
           add(new NonRepeatableSymbol());
           add(new SmallSymbolPrecedingLargeSymbol());
           add(new SubtractionPairsNotRepeated());
           add(new ThreeSymbolsInSuccession());
        }};
    }

    public boolean validate(List<RomanNumeral> romanNumerals) {
        return validateRomanNumerals(true, romanNumerals, romanNumeralValidators);
    }

    private boolean validateRomanNumerals(boolean valid, List<RomanNumeral> numerals, List<Validator> validators) {
        if (valid == false) return false;
        if (validators.isEmpty()) return true;

        boolean isValid = head(validators).validate(numerals);

        return validateRomanNumerals(isValid, numerals, tail(validators));

    }

    private Validator head(List<Validator> validators) {
        return validators.get(0);
    }

    private List<Validator> tail(List<Validator> validators) {
        return validators.subList(1, validators.size());
    }

}
