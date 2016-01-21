package com.translator.application;

import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;
import java.util.Map;

import static com.translator.domain.model.calculator.Credits.credits;
import static com.translator.domain.model.material.Material.aMaterial;
import static com.translator.domain.model.numeral.RomanNumeralAmount.aRomanNumeralAmount;

public class AnswerRomanNumeralsWorth extends AbstractAnsweringService {

    public AnswerRomanNumeralsWorth(Map<String, RomanNumeral> intergalacticToRoman) {
        super(intergalacticToRoman);
    }

    public String calculateWorth(String question) {
        String output;

        try {
            String quantityText = extractQuestionDetails(question);

            List<RomanNumeral> numeralQuantities = romanNumeralsFrom(quantityText);

            Credits worth = creditsCalculator().calculate(aRomanNumeralAmount(numeralQuantities), aMaterial("", credits(1.0)));

            output = quantityText + " is " + worth.amount();

        } catch (TranslationException te) {
            output = "I have no idea what you are talking about";
        }

        return output;
    }

}
