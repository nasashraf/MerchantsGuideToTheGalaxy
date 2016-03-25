package com.translator.application;

import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.Map;

public class AnsweringFactory {

    private AbstractRomanNumeralFactory romanNumeralFactory;

    public AnsweringFactory(AbstractRomanNumeralFactory romanNumeralFactory) {
        this.romanNumeralFactory = romanNumeralFactory;
    }

    public AnsweringService create(String question, Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName)  {

        AnsweringService answeringService;

        QuantityParser quantityParser = new QuantityParser(intergalacticToRoman);
        quantityParser.setCalculator(romanNumeralFactory.createCalculator());
        quantityParser.setValidator(romanNumeralFactory.createValidator());

        if (question.trim().startsWith("how much is")) {
            answeringService = new AnswerRomanNumeralsWorth(quantityParser);
        } else {
            answeringService = new AnswerMaterialWorth(materialsByName, quantityParser);
        }

        return answeringService;
    }
}
