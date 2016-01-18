package com.translator.domain.model.material;

import com.translator.domain.model.calculator.Credits;

public class Material {
    private String name;
    private Credits credits;

    public Material(String name, Credits credits) {
        this.name = name;
        this.credits = credits;
    }

    public static Material aMaterial(String name, Credits credits) {
        return new Material(name, credits);
    }

    public Credits cost() {
        return credits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material)) return false;

        Material material = (Material) o;

        if (!name.equals(material.name)) return false;
        return credits.equals(material.credits);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + credits.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Material{" + name + ", " + credits + "}";
    }
}
