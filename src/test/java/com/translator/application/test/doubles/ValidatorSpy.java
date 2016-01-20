package com.translator.application.test.doubles;

import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.domain.model.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class ValidatorSpy implements Validator {
    private boolean isValid;
    public List<List<RomanNumeral>> romanNumeralsCalledWithInOrder;

    public ValidatorSpy() {
        romanNumeralsCalledWithInOrder = new ArrayList<List<RomanNumeral>>();
    }

    public boolean validate(List<RomanNumeral> romanNumerals) {
        romanNumeralsCalledWithInOrder.add(romanNumerals);
        return isValid;
    }

    public void setValidationResult(boolean isValid) {
        this.isValid = isValid;
    }
}
