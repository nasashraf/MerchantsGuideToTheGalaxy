package com.translator.application;

import com.translator.domain.model.calculator.Calculator;
import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.calculator.CreditsCalculator;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.domain.model.validation.RomanNumeralValidator;
import com.translator.domain.model.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.translator.domain.model.numeral.RomanNumeralAmount.aRomanNumeralAmount;
import static java.util.Arrays.asList;

public class IntergalacticTranslator {

    public static final String EXTRACT_QUANTITY_AND_MATERIAL_NAME = "(?<=\\sis\\s)(.*)[^?]";
    public static final String SINGLE_WHITE_SPACE = " ";
    public static final Object DOES_NOT_EXIST = null;

    private Map<String, RomanNumeral> intergalacticToRoman;
    private Map<String, Material> materialsByName;
    private Calculator creditsCalculator;
    private Validator validator;

    public IntergalacticTranslator(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName) {
        this.intergalacticToRoman = intergalacticToRoman;
        this.materialsByName = materialsByName;
        this.creditsCalculator = new CreditsCalculator();
        this.validator = new RomanNumeralValidator();
    }

    public String translate(String question) {
        String output;

        try {
            String quantityAndMaterialText = extractQuantityAndMaterialTextFrom(question);

            String materialName = materialNameFrom(quantityAndMaterialText);
            Material material = getMaterialUsing(materialName);

            String quantitiesText = quantitiesTextFrom(quantityAndMaterialText);
            List<RomanNumeral> numeralQuantities = romanNumeralsFrom(quantitiesText);

            Credits worth = creditsCalculator.calculate(aRomanNumeralAmount(numeralQuantities), material);

            output = quantitiesText + " " + materialName + " is " + worth.amount() + " Credits";
        } catch (TranslationException te) {
            output = "I have no idea what you are talking about";
        }

        return output;
    }

    private String extractQuantityAndMaterialTextFrom(String question) {
        Pattern quantityAndMaterialNameRegexPattern = Pattern.compile(EXTRACT_QUANTITY_AND_MATERIAL_NAME);
        Matcher matcher = quantityAndMaterialNameRegexPattern.matcher(question);

        if (!matcher.find()) {
            throw new TranslationException();
        }

        return matcher.group().trim();
    }

    private String materialNameFrom(String quantityAndMaterialText) {
        int materialNamePosInText = quantityAndMaterialText.lastIndexOf(SINGLE_WHITE_SPACE) + 1;
        return quantityAndMaterialText.trim().substring(materialNamePosInText);
    }

    private String quantitiesTextFrom(String quantityAndMaterialText) {
        int endPosOfQuantitiesText = quantityAndMaterialText.lastIndexOf(SINGLE_WHITE_SPACE);
        return quantityAndMaterialText.trim().substring(0, endPosOfQuantitiesText);
    }

    private Material getMaterialUsing(String materialName) {
        Material material = materialsByName.get(materialName);

        if (material == DOES_NOT_EXIST) {
            throw new TranslationException();
        }

        return material;
    }

    private List<RomanNumeral> romanNumeralsFrom(String intergalacticQuantitiesText) {
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

    protected void setCalculator(Calculator calculator) {
        this.creditsCalculator = calculator;
    }

    protected void setValidator(Validator validator) {
        this.validator = validator;
    }

    public class TranslationException extends RuntimeException { }
}
