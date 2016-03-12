package com.translator.domain.model.numeral;

import com.translator.domain.model.calculator.Credits;

public class DivideMaterialCost implements MaterialOperation {

    public Credits operate(Credits first, Credits second) {
        return second.dividedBy(first);
    }
}
