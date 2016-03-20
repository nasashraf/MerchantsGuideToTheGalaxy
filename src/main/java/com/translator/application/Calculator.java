package com.translator.application;

import com.translator.domain.model.credits.Credits;
import com.translator.domain.model.numeral.Cost;

import java.util.List;

public interface Calculator {

    Credits calculate(List<? extends Cost> elements);
}
