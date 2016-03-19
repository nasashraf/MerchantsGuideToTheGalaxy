package com.translator.application;

import com.translator.domain.model.calculator.Calculator;
import com.translator.domain.model.calculator.CostCalculator;
import com.translator.domain.model.validation.RomanNumeralValidator;
import com.translator.domain.model.validation.Validator;

public abstract class AbstractAnsweringService implements AnsweringService {


    protected Calculator creditsCalculator;
    protected Validator validator;

    public AbstractAnsweringService() {
        this.creditsCalculator = new CostCalculator();
        this.validator = new RomanNumeralValidator();
    }


    protected Validator validator() {
        return validator;
    }

    protected void setCalculator(Calculator calculator) {
        this.creditsCalculator = calculator;
    }

    protected void setValidator(Validator validator) {
        this.validator = validator;
    }


}
