package com.translator.application.adapters;

import com.translator.domain.model.credits.Credits;
import com.translator.domain.model.material.MaterialFactory;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.domain.model.validation.RomanNumeralValidator;
import com.translator.domain.model.validation.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.translator.domain.model.credits.Credits.credits;
import static java.util.Arrays.asList;

public class MaterialsAdapter {

    private static final String WHITE_SPACE = " ";
    public static final int MATERIAL_NAME_AND_AMOUNT_POS = 0;
    public static final int TOTAL_CREDITS_COST_POS = 1;
    private final Map<String, RomanNumeral> intergalacticToRoman;
    private MaterialFactory materialFactory;
    private Validator validator;

    public MaterialsAdapter(Map<String, RomanNumeral> intergalacticToRoman) {
        this.intergalacticToRoman = intergalacticToRoman;
        materialFactory = new MaterialFactory();
        validator = new RomanNumeralValidator();
    }

    public Map<String, Material> createMaterialBasedOnPricePerUnit(final List<String> materialCostTranslations) {

        Map<String, Material> materials = new HashMap<String, Material>();

        for (String materialCostTranslation : materialCostTranslations) {
            String[] materialAmountAndTotalCostElements = divideTextByMaterialAmountAndTotalCost(materialCostTranslation);
            List<String> quantityAndMaterial = getQuantityAndMaterialNameFrom(materialAmountAndTotalCostElements);

            String materialName = materialNameFrom(quantityAndMaterial);
            List<RomanNumeral> numerals = romanNumeralQuantityFrom(quantityAndMaterial);
            Credits amountInCredits = getCreditsFrom(materialAmountAndTotalCostElements);

            if (!validator.validate(numerals)) {
                throw new MaterialsAdapterException();
            }

            Material material = materialFactory.createUsing(numerals, materialName, amountInCredits);
            materials.put(materialName, material);
        }

        return materials;
    }

    private List<String> getQuantityAndMaterialNameFrom(String[] materialAmountAndCostElements) {
        String elementsContainingQuantityANdMaterialName = materialAmountAndCostElements[MATERIAL_NAME_AND_AMOUNT_POS];
        return asList(elementsContainingQuantityANdMaterialName.split(WHITE_SPACE));
    }

    private Credits getCreditsFrom(String[] materialAmountAndTotalCostElements) {
        List<String> credits = asList(materialAmountAndTotalCostElements[TOTAL_CREDITS_COST_POS].split(WHITE_SPACE));
        String amount = credits.get(0);
        return credits(Double.parseDouble(amount));
    }

    private List<RomanNumeral> romanNumeralQuantityFrom(List<String> quantityAndMaterial) {
        List<String> intergalacticQuantities = getIntergalacticQuantities(quantityAndMaterial);

        List<RomanNumeral> numerals = new ArrayList<RomanNumeral>();
        for (String roman : intergalacticQuantities) {
            numerals.add(intergalacticToRoman.get(roman));
        }

        return numerals;
    }

    private List<String> getIntergalacticQuantities(List<String> quantityAndMaterial) {
        List<String> romanText = new ArrayList<String>();
        for (int i = 0; i < poisitionOfLastElement(quantityAndMaterial); i++) {
            romanText.add(quantityAndMaterial.get(i));
        }
        return romanText;
    }

    private String materialNameFrom(List<String> quantityAndMaterial) {
        return quantityAndMaterial.get(poisitionOfLastElement(quantityAndMaterial));
    }

    private int poisitionOfLastElement(List<String> quantityAndMaterial) {
        return quantityAndMaterial.size() - 1;
    }

    private String[] divideTextByMaterialAmountAndTotalCost(String materialCostTranslation) {
        return materialCostTranslation.split(" is ");
    }

    void setMaterialFactory(MaterialFactory materialFactory) {
        this.materialFactory = materialFactory;
    }

    void setValidator(Validator validator) {
        this.validator = validator;
    }

    public class MaterialsAdapterException  extends RuntimeException { }
}
