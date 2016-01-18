package com.translator.domain.model.calculator;

import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeralAmount;

public class CreditsCalculator {

    public Credits calculate(RomanNumeralAmount romanNumeralAmount, Material material) {
        return material.cost().multipliedBy(romanNumeralAmount.decimalValue());
    }
}
