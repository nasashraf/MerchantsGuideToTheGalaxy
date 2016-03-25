package com.translator.application;

import com.translator.domain.model.numeral.Calculator;
import com.translator.domain.model.numeral.RomanNumeralCalculator;
import com.translator.domain.model.validation.RomanNumeralValidator;
import com.translator.domain.model.validation.Validator;

public class ProductionRomanNumeralFactory extends AbstractRomanNumeralFactory {

    @Override
    public Calculator createCalculator() {
        return new RomanNumeralCalculator();
    }

    @Override
    public Validator createValidator() {
        return new RomanNumeralValidator();
    }
}
