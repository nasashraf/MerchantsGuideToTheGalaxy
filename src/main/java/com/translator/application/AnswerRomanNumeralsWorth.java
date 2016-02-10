package com.translator.application;

import com.translator.domain.model.calculator.CostCalculator;
import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;
import java.util.Map;

public class AnswerRomanNumeralsWorth extends AbstractAnsweringService {

    public AnswerRomanNumeralsWorth(Map<String, RomanNumeral> intergalacticToRoman) {
        super(intergalacticToRoman);
    }

    public String calculateWorth(String question) {
        String answer;

        try {
            String quantityText = extractQuestionDetails(question);

            List<RomanNumeral> numeralQuantities = romanNumeralsFrom(quantityText);

            answer = answerText(quantityText, creditsCalculator().calculate(numeralQuantities));
        } catch (TranslationException te) {
            answer = "I have no idea what you are talking about";
        }

        return answer;
    }

    protected String answerText(String quantities, Credits worth) {
        return quantities + " " + "is " + worth.amount();
    }

}
