package com.translator.domain.model.numeral;

import com.translator.domain.model.calculator.Credits;

import static com.translator.domain.model.numeral.MaterialOperation.MULTIPLY;

public class Material implements Cost {

    private Credits costPerUnit;
    private String name;
    private MaterialOperation materialOperation;

    public Material(String name, Credits costPerUnit, MaterialOperation operation) {
        this.name = name;
        this.costPerUnit = costPerUnit;
        this.materialOperation = operation;
    }

    public Material(String name, Credits costPerUnit) {
        this.name = name;
        this.costPerUnit = costPerUnit;
        this.materialOperation = MULTIPLY;
    }

    public static Material aMaterial(String name, Credits costPerUnit) {
        return new Material(name, costPerUnit);
    }

    public Credits operation(Credits number) {
        return materialOperation.operate(costPerUnit, number);
    }

    public Cost next(Cost nextElement) {
        return nextElement;
    }

    public String name() {
        return name;
    }

    public Credits cost() {
        return costPerUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Material that = (Material) o;

        if (!costPerUnit.equals(that.costPerUnit)) return false;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = costPerUnit.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Material{" +
                "costPerUnit=" + costPerUnit +
                ", name='" + name + '\'' +
                '}';
    }
}
