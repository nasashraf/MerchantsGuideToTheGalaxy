package com.translator.domain.model.numeral;

import com.translator.domain.model.calculator.Credits;

public interface Cost {

    Credits operation(Credits number);

    Cost next(Cost nextElement);

    Credits value();

}
