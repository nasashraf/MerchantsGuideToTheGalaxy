package com.translator.domain.model.calculator;

import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeralAmount;

public interface Calculator {

    public Credits calculate(RomanNumeralAmount romanNumeralAmount, Material material);
}
