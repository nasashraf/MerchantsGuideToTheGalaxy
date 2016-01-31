package com.translator.domain.model.numeral;

public interface Cost {

    Double value();

    Double operation(Double number);

    Cost next(Cost nextElement);
}
