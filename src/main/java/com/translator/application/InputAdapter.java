package com.translator.application;

import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.infrastructure.Console;
import com.translator.infrastructure.Screen;

import java.util.List;
import java.util.Map;

import static com.translator.application.InputCategoriser.*;

public class InputAdapter {

    private Console console;

    public InputAdapter() {
        this.console = new Screen();
    }

    public void adaptAndProcess(List<String> input) {

        try {
            Map<String, List<String>> categories = createInputCategoriser().categorise(input);

            Map<String, RomanNumeral> intergalacticToRoman = createIntergalacticToRomanAdapter().convert(categories.get(CONVERSIONS));

            Map<String, Material> materialsByName = createMaterialsAdapter(intergalacticToRoman).createMaterialBasedOnPricePerUnit(categories.get(MATERIAL_COSTS));

            createIntergalacticTranslationProcessor(intergalacticToRoman, materialsByName).process(categories.get(QUESTIONS));

        } catch (IntergalacticToRomanAdapter.TranslationException romanAdapterException) {
            console.write("ERROR - failed to convert intergalactic phrase to a valid roman numeral");
        } catch (MaterialsAdapter.MaterialsAdapterException materialsAdapterException) {
            console.write("ERROR - failed to determine the cost of materials");
        }
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

    protected void setConsole(Console console) {
        this.console = console;
    }
}
