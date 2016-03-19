package com.translator.application;

import com.translator.domain.model.calculator.Calculator;
import com.translator.domain.model.calculator.CostCalculator;
import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.domain.model.validation.RomanNumeralValidator;
import com.translator.domain.model.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class QuantityParser {

    private static final String SINGLE_WHITE_SPACE = " ";
    private static final Object DOES_NOT_EXIST = null;

    private Calculator creditsCalculator;
    private Validator validator;
    private Map<String, RomanNumeral> intergalacticToRoman;

    QuantityParser(Map<String, RomanNumeral> intergalacticToRoman) {
        this.creditsCalculator = new CostCalculator();
        this.validator = new RomanNumeralValidator();
        this.intergalacticToRoman = intergalacticToRoman;
    }

    Credits quantityFrom(String quantityText) {
        List<RomanNumeral> numerals = romanNumeralsFrom(quantityText);

        if (!validator.validate(numerals)) {
            throw new TranslationException();
        }

        return creditsCalculator.calculate(numerals);
    }

    List<RomanNumeral> romanNumeralsFrom(String intergalacticQuantitiesText) {
        List<RomanNumeral> numeralQuantities = new ArrayList<RomanNumeral>();

        List<String> quantities = asList(intergalacticQuantitiesText.split(SINGLE_WHITE_SPACE));

        for(String intergalacticQuantity : quantities) {
            RomanNumeral romanNumeral = intergalacticToRoman.get(intergalacticQuantity);

            if (romanNumeral == DOES_NOT_EXIST) {
                throw new TranslationException();
            }

            numeralQuantities.add(romanNumeral);
        }

        return numeralQuantities;
    }


    protected void setCalculator(Calculator calculator) {
        this.creditsCalculator = calculator;
    }

    protected void setValidator(Validator validator) {
        this.validator = validator;
    }
}
