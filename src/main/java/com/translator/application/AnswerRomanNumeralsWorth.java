package com.translator.application;

import com.translator.domain.model.calculator.Calculator;
import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.domain.model.validation.Validator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnswerRomanNumeralsWorth implements AnsweringService {

    private static final String EXTRACT_QUESTION_DETAILS = "(?<=\\sis\\s)(.*)[^?]";

    private Map<String, RomanNumeral> intergalacticToRoman;
    private Calculator creditsCalculator;
    private Validator validator;

    public AnswerRomanNumeralsWorth(Map<String, RomanNumeral> intergalacticToRoman, Calculator creditsCalculator, Validator validator) {
        this.intergalacticToRoman = intergalacticToRoman;
        this.creditsCalculator = creditsCalculator;
        this.validator = validator;
    }

    public String calculateWorth(String question) {
        String answer;

        try {
            QuantityParser quantityParser = new QuantityParser(intergalacticToRoman);
            quantityParser.setCalculator(creditsCalculator);
            quantityParser.setValidator(validator);

            Pattern quantityAndMaterialNameRegexPattern = Pattern.compile(EXTRACT_QUESTION_DETAILS);
            Matcher matcher = quantityAndMaterialNameRegexPattern.matcher(question);

            if (!matcher.find()) {
                throw new TranslationException();
            }

            String quantityText = matcher.group().trim();

            answer = answerText(quantityText, quantityParser.quantityFrom(quantityText));
        } catch (TranslationException te) {
            answer = "I have no idea what you are talking about";
        }

        return answer;
    }

    protected String answerText(String quantities, Credits worth) {
        return quantities + " " + "is " + worth.amount();
    }

}
