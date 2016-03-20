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
    private QuantityParser quantityParser;

    public AnswerRomanNumeralsWorth(Map<String, RomanNumeral> intergalacticToRoman, Calculator creditsCalculator, Validator validator) {
        this.intergalacticToRoman = intergalacticToRoman;
        quantityParser = new QuantityParser(intergalacticToRoman);
        quantityParser.setCalculator(creditsCalculator);
        quantityParser.setValidator(validator);
    }

    public String calculateWorth(String question) {
        String answer;

        try {
            String quantityText = extractQuestionDetails(question);

            answer = answerText(quantityText, quantityParser.quantityFrom(quantityText));
        } catch (TranslationException te) {
            answer = "I have no idea what you are talking about";
        }

        return answer;
    }

    private String extractQuestionDetails(String question) {
        Pattern quantityAndMaterialNameRegexPattern = Pattern.compile(EXTRACT_QUESTION_DETAILS);
        Matcher matcher = quantityAndMaterialNameRegexPattern.matcher(question);

        if (!matcher.find()) {
            throw new TranslationException();
        }

        return matcher.group().trim();
    }

    private String answerText(String quantities, Credits worth) {
        return quantities + " " + "is " + worth.amount();
    }

}
