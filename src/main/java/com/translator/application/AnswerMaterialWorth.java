package com.translator.application;

import com.translator.domain.model.calculator.CostCalculator;
import com.translator.domain.model.credits.Credits;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.domain.model.validation.RomanNumeralValidator;
import com.translator.domain.model.validation.Validator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnswerMaterialWorth implements AnsweringService {

    private static final String EXTRACT_QUESTION_DETAILS = "(?<=\\sis\\s)(.*)[^?]";
    public static final String SINGLE_WHITE_SPACE = " ";
    public static final Object DOES_NOT_EXIST = null;

    private Map<String, Material> materialsByNameLookup;
    private Map<String, RomanNumeral> intergalacticToRoman;
    private QuantityParser quantityParser;

    public AnswerMaterialWorth(Map<String, RomanNumeral> intergalacticToRomanTranslation, Map<String, Material> materialsByNameLookup, Calculator creditsCalculator, Validator validator) {
        this.materialsByNameLookup = materialsByNameLookup;
        this.intergalacticToRoman = intergalacticToRomanTranslation;
        quantityParser = new QuantityParser(intergalacticToRoman);
        quantityParser.setCalculator(creditsCalculator);
        quantityParser.setValidator(validator);
    }

    public AnswerMaterialWorth(Map<String, RomanNumeral> intergalacticToRomanTranslation, Map<String, Material> materialsByNameLookup) {
        this.materialsByNameLookup = materialsByNameLookup;
        this.intergalacticToRoman = intergalacticToRomanTranslation;
        quantityParser = new QuantityParser(intergalacticToRoman);
        quantityParser.setCalculator(new CostCalculator());
        quantityParser.setValidator(new RomanNumeralValidator());
    }

    public String calculateWorth(String question) {
        String answer;

        try {
            String quantityAndMaterialText = extractQuestionDetails(question);

            Material material = createMaterial(quantityAndMaterialText);

            Credits amount = quantityParser.quantityFrom(quantitiesTextFrom(quantityAndMaterialText));

            Credits worth = material.costOf(amount);

            answer = answerText(quantitiesTextFrom(quantityAndMaterialText), material.name(), worth.amount());
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

    private String quantitiesTextFrom(String quantityAndMaterialText) {
        int endPosOfQuantitiesText = quantityAndMaterialText.lastIndexOf(SINGLE_WHITE_SPACE);
        return quantityAndMaterialText.trim().substring(0, endPosOfQuantitiesText);
    }

    private Material createMaterial(String materialDetails) {
        String materialName = materialNameFrom(materialDetails);
        return getMaterialUsing(materialName);
    }

    private String materialNameFrom(String quantityAndMaterialText) {
        int materialNamePosInText = quantityAndMaterialText.lastIndexOf(SINGLE_WHITE_SPACE) + 1;
        return quantityAndMaterialText.trim().substring(materialNamePosInText);
    }

    private Material getMaterialUsing(String materialName) {
        Material material = materialsByNameLookup.get(materialName);

        if (material == DOES_NOT_EXIST) {
            throw new TranslationException();
        }

        return material;
    }

    private String answerText(String quantities, String materialName, String worthAmount) {
        return quantities + " " + materialName + " is " + worthAmount + " Credits";
    }

}
