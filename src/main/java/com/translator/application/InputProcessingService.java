package com.translator.application;

import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.infrastructure.Console;
import com.translator.infrastructure.Screen;

import java.util.List;
import java.util.Map;

import static com.translator.application.InputCategoriser.*;

public class InputProcessingService {

    private Console console;

    public InputProcessingService() {
        this.console = new Screen();
    }

    public void adaptAndProcess(List<String> input) {

        try {
            Map<String, List<String>> categories = createInputCategoriser().categorise(input);

            Map<String, RomanNumeral> intergalacticToRomanLookup = createIntergalacticToRomanAdapter().convert(categories.get(CONVERSIONS));

            Map<String, Material> materialsByNameLookup = createMaterialsAdapter(intergalacticToRomanLookup).createMaterialBasedOnPricePerUnit(categories.get(MATERIAL_COSTS));

            intergalacticTranslationProcessor(intergalacticToRomanLookup, materialsByNameLookup).process(categories.get(QUESTIONS));

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

    protected IntergalacticWorthCalculationProcessor intergalacticTranslationProcessor(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName) {
        return new IntergalacticWorthCalculationProcessor(intergalacticToRoman, materialsByName);
    }

    protected void setConsole(Console console) {
        this.console = console;
    }
}
