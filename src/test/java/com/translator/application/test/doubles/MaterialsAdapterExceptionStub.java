package com.translator.application.test.doubles;

import com.translator.application.MaterialsAdapter;
import com.translator.domain.model.numeral.Material;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;
import java.util.Map;

public class MaterialsAdapterExceptionStub extends MaterialsAdapter {
    public MaterialsAdapterExceptionStub(Map<String, RomanNumeral> intergalacticToRoman) {
        super(intergalacticToRoman);
    }

    public Map<String, Material> createMaterialBasedOnPricePerUnit(final List<String> materialCostTranslations) {
        throw new MaterialsAdapter.MaterialsAdapterException();
    }
}
