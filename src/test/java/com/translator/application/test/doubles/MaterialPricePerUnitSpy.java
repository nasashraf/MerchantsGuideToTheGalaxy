package com.translator.application.test.doubles;

import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.material.MaterialPricePerUnit;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;

public class MaterialPricePerUnitSpy extends MaterialPricePerUnit {

    private Material material;
    private List<RomanNumeral> romanNumerals;
    private String materialName;
    private Credits cost;


    @Override
    public Material material(List<RomanNumeral> romanNumerals, String materialName, Credits cost) {
        this.romanNumerals = romanNumerals;
        this.materialName = materialName;
        this.cost = cost;

        return material;
    }

    public void setMaterial(Material materialStub) {
        this.material = materialStub;
    }

    public List<RomanNumeral> romanNumerals() {
        return romanNumerals;
    }

    public String materialName() {
        return materialName;
    }

    public Credits cost() {
        return cost;
    }
}
