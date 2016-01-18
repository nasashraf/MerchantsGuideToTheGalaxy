package com.translator.domain.model.material;

import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.numeral.RomanNumeralAmount;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;

public class MaterialPricePerUnit {

    public Material material(List<RomanNumeral> romanNumerals, String materialName, Credits cost) {
        RomanNumeralAmount romanNumeralAmount = new RomanNumeralAmount(romanNumerals);

        Credits pricePerUnit = cost.dividedBy(romanNumeralAmount.decimalValue());

        return new Material(materialName, pricePerUnit);
    }
}
