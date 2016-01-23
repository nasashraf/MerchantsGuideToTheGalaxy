package com.translator.application;

import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.material.Material;
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
        String answer;

        try {
            String quantityText = extractQuestionDetails(question);

            List<RomanNumeral> numeralQuantities = romanNumeralsFrom(quantityText);

            Material material = createMaterial(quantityText);

            Credits worth = creditsCalculator().calculate(aRomanNumeralAmount(numeralQuantities), material);

            answer = answerText(quantityText, material.name(), worth.amount());

        } catch (TranslationException te) {
            answer = "I have no idea what you are talking about";
        }

        return answer;
    }


    protected Material createMaterial(String materialDetails) {
        return aMaterial("", credits(1.0));
    }

    protected String answerText(String quantities, String materialName, String worthAmount) {
        return quantities + " " + materialName + "is " + worthAmount;
    }

}
