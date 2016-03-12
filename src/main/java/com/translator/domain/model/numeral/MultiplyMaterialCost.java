package com.translator.domain.model.numeral;

import com.translator.domain.model.calculator.Credits;

public class MultiplyMaterialCost implements MaterialOperation {

    public Credits operate(Credits first, Credits second) {
        return first.multipliedBy(second);
    }
}
