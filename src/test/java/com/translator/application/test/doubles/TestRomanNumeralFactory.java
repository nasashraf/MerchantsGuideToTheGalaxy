package com.translator.application.test.doubles;

import com.translator.application.AbstractRomanNumeralFactory;
import com.translator.domain.model.numeral.Calculator;
import com.translator.domain.model.validation.Validator;

public class TestRomanNumeralFactory extends AbstractRomanNumeralFactory {

    private CalculatorSpy calculatorSpy;
    private ValidatorSpy validatorSpy;

    public TestRomanNumeralFactory(CalculatorSpy calculatorSpy, ValidatorSpy validatorSpy) {
        this.calculatorSpy = calculatorSpy;
        this.validatorSpy = validatorSpy;
    }

    @Override
    public Calculator createCalculator() {
        return calculatorSpy;
    }

    @Override
    public Validator createValidator() {
        return validatorSpy;
    }
}
