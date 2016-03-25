package com.translator.application;

import com.translator.domain.model.numeral.Calculator;
import com.translator.domain.model.validation.Validator;

public abstract class AbstractRomanNumeralFactory {

    abstract public Calculator createCalculator();

    abstract public Validator createValidator();
}
