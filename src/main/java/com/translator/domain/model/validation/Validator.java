package com.translator.domain.model.validation;

import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;

public interface Validator {

    boolean validate(List<RomanNumeral> romanNumerals);
}
