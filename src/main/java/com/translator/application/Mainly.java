package com.translator.application;

import com.translator.domain.model.numeral.RomanNumeral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mainly {

    public void go(List<String> all) {

        Map<String, List<String>> translationAndQuestions = getTranslationAndQuestions(all);   // question mark or not

        List<String> intergalacticConversionText = translationAndQuestions.get("translation");
        List<String> materialCostConversionText =translationAndQuestions.get("material");
        List<String> questions = translationAndQuestions.get("questions");

        IntergalacticToRomanAdapter intergalacticToRomanAdapter = new IntergalacticToRomanAdapter();
        Map<String, RomanNumeral> intergalacticToRoman = intergalacticToRomanAdapter.convert(intergalacticConversionText);


        MaterialsAdapter materialsAdapter = new MaterialsAdapter(intergalacticToRoman);

//        Map<String, Material> materialsByName = new HashMap<String, Material>();
//
//        for (String materialCostConversion : materialCostConversionText) {
//            Material material = materialsAdapter.createMaterialBasedOnPricePerUnit(materialCostConversion);
//            materialsByName.put(material.name(), material);
//        }
//
//        IntergalacticTranslator intergalacticTranslator = new IntergalacticTranslator(intergalacticToRoman, materialsByName);
//
//        for (String question : questions) {
//            String result = intergalacticTranslator.translate(question);
//            Console.write(result);
//        }

    }

    private Map<String, List<String>> getTranslationAndQuestions(List<String> all) {
        Map<String, List<String>> translationAndQuestions = new HashMap<String, List<String>>();

        List<String> conversion = new ArrayList<String>();
        List<String> materials = new ArrayList<String>();
        List<String> questions = new ArrayList<String>();



        for (String text : all) {
            if (text.endsWith("?")) {
                questions.add(text);
            } else if (text.endsWith("Credits")) {
                materials.add(text);
            } else {
                conversion.add(text);
            }
        }

        return null;
    }
}
