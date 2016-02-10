package com.translator.domain.model.calculator;

import com.translator.domain.model.numeral.Cost;

import java.util.List;

public interface Calculator {

    public Credits calculate(List<? extends Cost> elements);
}
