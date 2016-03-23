package com.translator.domain.model.material;

import com.translator.domain.model.numeral.RomanNumeralCalculator;
import com.translator.domain.model.credits.Credits;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;

public class MaterialFactory {

    public Material createUsing(List<RomanNumeral> romanNumerals, String materialName, Credits cost) {
        RomanNumeralCalculator costCalculator = new RomanNumeralCalculator();

        Credits pricePerUnit = cost.dividedBy(costCalculator.calculate(romanNumerals));

        return new Material(materialName, pricePerUnit);
    }
}
