package com.translator.application;

import com.translator.domain.model.calculator.Calculator;
import com.translator.domain.model.calculator.CreditsCalculator;
import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.domain.model.validation.RomanNumeralValidator;
import com.translator.domain.model.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public abstract class AbstractAnsweringService implements AnsweringService {

    private static final String EXTRACT_QUESTION_DETAILS = "(?<=\\sis\\s)(.*)[^?]";
    private static final String SINGLE_WHITE_SPACE = " ";
    private static final Object DOES_NOT_EXIST = null;

    private Calculator creditsCalculator;
    private Validator validator;
    private Map<String, RomanNumeral> intergalacticToRoman;

    public AbstractAnsweringService(Map<String, RomanNumeral> intergalacticToRoman) {
        this.creditsCalculator = new CreditsCalculator();
        this.validator = new RomanNumeralValidator();
        this.intergalacticToRoman = intergalacticToRoman;
    }

    protected String quantitiesTextFrom(String quantityAndMaterialText) {
        int endPosOfQuantitiesText = quantityAndMaterialText.lastIndexOf(SINGLE_WHITE_SPACE);
        return quantityAndMaterialText.trim().substring(0, endPosOfQuantitiesText);
    }

    protected String extractQuestionDetails(String question) {
        Pattern quantityAndMaterialNameRegexPattern = Pattern.compile(EXTRACT_QUESTION_DETAILS);
        Matcher matcher = quantityAndMaterialNameRegexPattern.matcher(question);

        if (!matcher.find()) {
            throw new TranslationException();
        }

        return matcher.group().trim();
    }

    protected List<RomanNumeral> romanNumeralsFrom(String intergalacticQuantitiesText) {
        List<RomanNumeral> numeralQuantities = new ArrayList<RomanNumeral>();

        List<String> quantities = asList(intergalacticQuantitiesText.split(SINGLE_WHITE_SPACE));

        for(String intergalacticQuantity : quantities) {
            RomanNumeral romanNumeral = intergalacticToRoman.get(intergalacticQuantity);

            if (romanNumeral == DOES_NOT_EXIST) {
                throw new TranslationException();
            }

            numeralQuantities.add(romanNumeral);
        }

        if (!validator.validate(numeralQuantities)) {
            throw new TranslationException();
        }

        return numeralQuantities;
    }

    protected Calculator creditsCalculator() {
        return creditsCalculator;
    }

    protected Validator validator() {
        return validator;
    }

    protected void setCalculator(Calculator calculator) {
        this.creditsCalculator = calculator;
    }

    protected void setValidator(Validator validator) {
        this.validator = validator;
    }

    public class TranslationException extends RuntimeException { }

}
