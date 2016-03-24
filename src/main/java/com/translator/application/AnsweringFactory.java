package com.translator.application;

import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.Calculator;
import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.domain.model.validation.Validator;

import java.util.Map;

public class AnsweringFactory {

    private Calculator creditsCalculator;
    private Validator validator;

    public AnsweringFactory(Calculator creditsCalculator, Validator validator) {
        this.creditsCalculator = creditsCalculator;
        this.validator = validator;
    }

    public AnsweringService create(String question, Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName)  {

        AnsweringService answeringService;

        QuantityParser quantityParser = new QuantityParser(intergalacticToRoman);
        quantityParser.setCalculator(creditsCalculator);
        quantityParser.setValidator(validator);

        if (question.trim().startsWith("how much is")) {
            answeringService = new AnswerRomanNumeralsWorth(quantityParser);
        } else {
            answeringService = new AnswerMaterialWorth(materialsByName, quantityParser);
        }

        return answeringService;
    }
}
