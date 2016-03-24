package com.translator.domain.model.numeral;

import com.translator.domain.model.credits.Credits;

import java.util.List;

public interface Calculator {

    Credits calculate(List<? extends Cost> elements);
}
