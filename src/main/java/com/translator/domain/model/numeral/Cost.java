package com.translator.domain.model.numeral;

import com.translator.domain.model.calculator.Credits;

public interface Cost {

    Credits value();

    Credits operation(Credits number);

    Cost next(Cost nextElement);

}
