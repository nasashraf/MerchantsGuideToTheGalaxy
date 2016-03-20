package com.translator.domain.model.material;

import com.translator.domain.model.calculator.Credits;

public class Material  {

    private Credits costPerUnit;
    private String name;


    public Material(String name, Credits costPerUnit) {
        this.name = name;
        this.costPerUnit = costPerUnit;
    }

    public static Material aMaterial(String name, Credits costPerUnit) {
        return new Material(name, costPerUnit);
    }

    public String name() {
        return name;
    }

    public Credits cost() {
        return costPerUnit;
    }

    public Credits costOf(Credits amount) {
        return costPerUnit.multipliedBy(amount);
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
