package com.translator.application;

import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.numeral.Cost;
import com.translator.domain.model.numeral.Material;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnswerMaterialWorth extends AbstractAnsweringService {

    public static final String SINGLE_WHITE_SPACE = " ";
    public static final Object DOES_NOT_EXIST = null;

    private Map<String, Material> materialsByNameLookup;

    public AnswerMaterialWorth(Map<String, RomanNumeral> intergalacticToRomanTranslation, Map<String, Material> materialsByNameLookup) {
        super(intergalacticToRomanTranslation);
        this.materialsByNameLookup = materialsByNameLookup;
    }

    public String calculateWorth(String question) {
        String answer;

        try {
            String quantityAndMaterialText = extractQuestionDetails(question);

            Material material = createMaterial(quantityAndMaterialText);

            String quantitiesText = quantitiesTextFrom(quantityAndMaterialText);
            List<Cost> costs = new ArrayList<Cost>();
            costs.addAll(romanNumeralsFrom(quantitiesText));
            costs.add(material);

            Credits worth = creditsCalculator().calculate(costs);

            answer = answerText(quantitiesText, material.name(), worth.amount());
        } catch (TranslationException te) {
            answer = "I have no idea what you are talking about";
        }

        return answer;
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
