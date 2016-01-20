package com.translator.application;

import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;
import java.util.Map;

import static com.translator.application.InputCategoriser.*;

public class InputAdapter {

    public void adaptAndProcess(List<String> input) {
        Map<String, List<String>> categories = createInputCategoriser().categorise(input);

        Map<String, RomanNumeral> intergalacticToRoman = createIntergalacticToRomanAdapter().convert(categories.get(CONVERSIONS));

        Map<String, Material> materialsByName = createMaterialsAdapter(intergalacticToRoman).createMaterialBasedOnPricePerUnit(categories.get(MATERIAL_COSTS));

        createIntergalacticTranslationProcessor(intergalacticToRoman, materialsByName).process(categories.get(QUESTIONS));
    }

    protected InputCategoriser createInputCategoriser() {
        return new InputCategoriser();
    }

    protected IntergalacticToRomanAdapter createIntergalacticToRomanAdapter() {
        return new IntergalacticToRomanAdapter();
    }

    protected MaterialsAdapter createMaterialsAdapter(Map<String, RomanNumeral> intergalacticToRoman) {
        return new MaterialsAdapter(intergalacticToRoman);
    }

    protected IntergalacticTranslationProcessor createIntergalacticTranslationProcessor(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName) {
        return new IntergalacticTranslationProcessor(intergalacticToRoman, materialsByName);
    }
}
