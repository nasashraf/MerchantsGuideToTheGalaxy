package com.translator.domain.model.numeral;

import com.translator.domain.model.calculator.Credits;

import static com.translator.domain.model.calculator.Credits.credits;

public class Material implements Cost {

    public static Material NO_MATERIAL = new Material("", Credits.credits(1.0));

    private Credits costPerUnit;
    private String name;

    public Material(String name, Credits costPerUnit) {
        this.name = name;
        this.costPerUnit = costPerUnit;
    }

    public static Material aMaterial(String name, Credits costPerUnit) {
        return new Material(name, costPerUnit);
    }

    public Credits value() {
        return credits(0.0);
    }

    public Credits operation(Credits number) {
        return costPerUnit.multipliedBy(number);
    }

    public Cost next(Cost nextElement) {
        return nextElement;
    }

    public String name() {
        return name;
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
