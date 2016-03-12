package com.translator.domain.model.numeral;

import com.translator.domain.model.calculator.Credits;

public interface MaterialOperation {

    Credits operate(Credits first, Credits second);
}
