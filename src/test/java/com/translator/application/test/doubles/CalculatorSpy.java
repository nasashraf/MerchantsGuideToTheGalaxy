package com.translator.application.test.doubles;

import com.translator.application.Calculator;
import com.translator.domain.model.credits.Credits;
import com.translator.domain.model.numeral.Cost;

import java.util.ArrayList;
import java.util.List;


public class CalculatorSpy implements Calculator {

    private Credits amount;
    public List<List<? extends Cost>> romanNumeralAmountCalledWith;

    public CalculatorSpy() {
        this.romanNumeralAmountCalledWith = new ArrayList<List<? extends Cost>>();
    }


    public Credits calculate(List<? extends Cost> elements) {
        romanNumeralAmountCalledWith.add(elements);

        return amount;
    }

    public void setCreditsAmount(Credits amount) {
        this.amount = amount;
    }
}
