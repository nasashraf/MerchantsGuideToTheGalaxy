package com.translator.application;

import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.Map;

public class AnswerRomanNumeralsWorth extends AbstractAnsweringService {

    private Map<String, RomanNumeral> intergalacticToRoman;

    public AnswerRomanNumeralsWorth(Map<String, RomanNumeral> intergalacticToRoman) {
        super(intergalacticToRoman);
        this.intergalacticToRoman = intergalacticToRoman;
    }

    public String calculateWorth(String question) {
        String answer;

        try {
            QuantityParser quantityParser = new QuantityParser(intergalacticToRoman);
            quantityParser.setCalculator(creditsCalculator);
            quantityParser.setValidator(validator);

            String quantityText = extractQuestionDetails(question);

//            List<? extends Cost> numeralQuantities = romanNumeralsFrom(quantityText);

            answer = answerText(quantityText, quantityParser.quantityFrom(question));
        } catch (TranslationException te) {
            answer = "I have no idea what you are talking about";
        }

        return answer;
    }

    protected String answerText(String quantities, Credits worth) {
        return quantities + " " + "is " + worth.amount();
    }

}
