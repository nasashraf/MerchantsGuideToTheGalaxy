package com.translator.application.test.doubles;

import com.translator.domain.model.calculator.Calculator;
import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeralAmount;


public class CalculatorSpy implements Calculator {

    private Credits amount;
    public RomanNumeralAmount romanNumeralAmountCalledWith;
    public Material materialCalledWith;

    public Credits calculate(RomanNumeralAmount romanNumeralAmount, Material material) {
        romanNumeralAmountCalledWith = romanNumeralAmount;
        materialCalledWith = material;

        return amount;
    }

    public void setCreditsAmount(Credits amount) {
        this.amount = amount;
    }
}
