package com.translator.application.test.doubles;

import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.domain.model.validation.Validator;

import java.util.List;

public class ValidatorSpy implements Validator {
    private boolean isValid;
    public List<RomanNumeral> romanNumeralsCalledWithInOrder;

    public boolean validate(List<RomanNumeral> romanNumerals) {
        romanNumeralsCalledWithInOrder = romanNumerals;
        return isValid;
    }

    public void setValidationResult(boolean isValid) {
        this.isValid = isValid;
    }
}
