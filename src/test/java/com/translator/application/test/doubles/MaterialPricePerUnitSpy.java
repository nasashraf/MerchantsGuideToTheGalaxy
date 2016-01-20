package com.translator.application.test.doubles;

import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.material.MaterialPricePerUnit;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaterialPricePerUnitSpy extends MaterialPricePerUnit {

    private Map<String, Material> materials;
    private List<List<RomanNumeral>> romanNumerals;
    private List<String> materialName;
    private List<Credits> cost;

    public MaterialPricePerUnitSpy() {
        romanNumerals = new ArrayList<List<RomanNumeral>>();
        materialName = new ArrayList<String>();
        cost = new ArrayList<Credits>();

    }

    @Override
    public Material material(List<RomanNumeral> romanNumerals, String materialName, Credits cost) {
        this.romanNumerals.add(romanNumerals);
        this.materialName.add(materialName);
        this.cost.add(cost);

        return materials.get(materialName);
    }

    public void setMaterials(Map<String, Material> materialsStub) {
        this.materials = materialsStub;
    }

    public List<List<RomanNumeral>> romanNumerals() {
        return romanNumerals;
    }

    public List<String> materialName() {
        return materialName;
    }

    public List<Credits> cost() {
        return cost;
    }
}
