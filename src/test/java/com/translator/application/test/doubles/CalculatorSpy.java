package com.translator.application.test.doubles;

import com.translator.domain.model.calculator.Calculator;
import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeralAmount;

import java.util.ArrayList;
import java.util.List;


public class CalculatorSpy implements Calculator {

    private Credits amount;
    public List<RomanNumeralAmount> romanNumeralAmountCalledWith;
    public List<Material> materialCalledWith;

    public CalculatorSpy() {
        romanNumeralAmountCalledWith = new ArrayList<RomanNumeralAmount>();
        materialCalledWith = new ArrayList<Material>();
    }

    public Credits calculate(RomanNumeralAmount romanNumeralAmount, Material material) {
        romanNumeralAmountCalledWith.add(romanNumeralAmount);
        materialCalledWith.add(material);

        return amount;
    }

    public void setCreditsAmount(Credits amount) {
        this.amount = amount;
    }
}
