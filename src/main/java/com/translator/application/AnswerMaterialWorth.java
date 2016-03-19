package com.translator.application;

import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.numeral.Material;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnswerMaterialWorth extends AbstractAnsweringService {

    private static final String EXTRACT_QUESTION_DETAILS = "(?<=\\sis\\s)(.*)[^?]";
    public static final String SINGLE_WHITE_SPACE = " ";
    public static final Object DOES_NOT_EXIST = null;

    private Map<String, Material> materialsByNameLookup;
    private Map<String, RomanNumeral> intergalacticToRoman;

    public AnswerMaterialWorth(Map<String, RomanNumeral> intergalacticToRomanTranslation, Map<String, Material> materialsByNameLookup) {
        super();
        this.materialsByNameLookup = materialsByNameLookup;
        this.intergalacticToRoman = intergalacticToRomanTranslation;
    }

    public String calculateWorth(String question) {
        String answer;

        try {
            String quantityAndMaterialText = extractQuestionDetails(question);
            Material material = createMaterial(quantityAndMaterialText);

            QuantityParser quantityParser = new QuantityParser(intergalacticToRoman);
            quantityParser.setCalculator(creditsCalculator);
            quantityParser.setValidator(validator);

            Credits numeralAmount = quantityParser.quantityFrom(quantitiesTextFrom(quantityAndMaterialText));

            Credits worth = material.costOf(numeralAmount);

            answer = answerText(quantitiesTextFrom(quantityAndMaterialText), material.name(), worth.amount());
        } catch (TranslationException te) {
            answer = "I have no idea what you are talking about";
        }

        return answer;
    }


    protected String extractQuestionDetails(String question) {
        Pattern quantityAndMaterialNameRegexPattern = Pattern.compile(EXTRACT_QUESTION_DETAILS);
        Matcher matcher = quantityAndMaterialNameRegexPattern.matcher(question);

        if (!matcher.find()) {
            throw new TranslationException();
        }

        return matcher.group().trim();
    }
    protected String quantitiesTextFrom(String quantityAndMaterialText) {
        int endPosOfQuantitiesText = quantityAndMaterialText.lastIndexOf(SINGLE_WHITE_SPACE);
        return quantityAndMaterialText.trim().substring(0, endPosOfQuantitiesText);
    }

    protected Material createMaterial(String materialDetails) {
        return materialFrom(materialDetails);
    }

    private Material materialFrom(String quantityAndMaterialText) {
        String materialName = materialNameFrom(quantityAndMaterialText);
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


    protected String answerText(String quantities, String materialName, String worthAmount) {
        return quantities + " " + materialName + " is " + worthAmount + " Credits";
    }

}
