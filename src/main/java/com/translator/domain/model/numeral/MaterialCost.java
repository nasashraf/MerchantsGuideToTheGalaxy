package com.translator.domain.model.numeral;

import com.translator.domain.model.calculator.Credits;

public class MaterialCost implements Cost {

    private MaterialOperation materialOperation;
    private Material material;

    public MaterialCost(Material material, MaterialOperation operation) {
        this.material = material;
        this.materialOperation = operation;
    }


    public static MaterialCost aMaterialCost(Material material, MaterialOperation operation) {
        return new MaterialCost(material, operation);
    }

    public Credits operation(Credits number) {
        return materialOperation.operate(material.cost(), number);
    }

    public Cost next(Cost nextElement) {
        return nextElement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MaterialCost that = (MaterialCost) o;

        if (materialOperation != that.materialOperation) return false;
        return material.equals(that.material);

    }

    @Override
    public int hashCode() {
        int result = materialOperation.hashCode();
        result = 31 * result + material.hashCode();
        return result;
    }
}
