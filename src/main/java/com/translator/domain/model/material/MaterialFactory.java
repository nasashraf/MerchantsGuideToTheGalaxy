package com.translator.domain.model.material;

import com.translator.domain.model.calculator.CostCalculator;
import com.translator.domain.model.credits.Credits;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;

public class MaterialFactory {

    public Material createUsing(List<RomanNumeral> romanNumerals, String materialName, Credits cost) {
        CostCalculator costCalculator = new CostCalculator();

        Credits pricePerUnit = cost.dividedBy(costCalculator.calculate(romanNumerals));

        return new Material(materialName, pricePerUnit);
    }
}
