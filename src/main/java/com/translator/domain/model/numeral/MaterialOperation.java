package com.translator.domain.model.numeral;

import com.translator.domain.model.calculator.Credits;

public enum MaterialOperation {

    MULTIPLY {
        @Override
        public Credits operate(Credits first, Credits second) {
            return first.multipliedBy(second);
        }
    },
    DIVIDE {
        @Override
        public Credits operate(Credits first, Credits second) {
            return second.dividedBy(first);
        }
    };

    public abstract Credits operate(Credits first, Credits second);
}
