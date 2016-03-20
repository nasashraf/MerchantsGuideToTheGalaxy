package com.translator.application.test.doubles;

import com.translator.application.adapters.MaterialsAdapter;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;
import java.util.Map;

public class MaterialsAdapterSpy extends MaterialsAdapter {

    public List<String> createMaterialCalledWith;
    public Map<String, RomanNumeral> intergalacticToRomanCreatedWith;
    private Map<String, Material> materialStub;

    public MaterialsAdapterSpy(Map<String, RomanNumeral> intergalacticToRoman) {
        super(intergalacticToRoman);
        intergalacticToRomanCreatedWith = intergalacticToRoman;
    }

    @Override
    public Map<String, Material> createMaterialBasedOnPricePerUnit(final List<String> materialCostTranslation) {
        createMaterialCalledWith = materialCostTranslation;
        return materialStub;
    }

    public void setMaterialByStub(Map<String, Material> materialStub) {
        this.materialStub = materialStub;
    }
}