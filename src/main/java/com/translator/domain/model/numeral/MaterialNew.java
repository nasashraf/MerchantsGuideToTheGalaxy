package com.translator.domain.model.numeral;

public class MaterialNew implements Cost {

    private Double costPerUnit;
    private String name;

    public MaterialNew(String name, Double costPerUnit) {
        this.name = name;
        this.costPerUnit = costPerUnit;
    }

    public static MaterialNew aMaterial(String name, Double costPerUnit) {
        return new MaterialNew(name, costPerUnit);
    }

    public Double value() {
        return 0.0;
    }

    public Double operation(Double number) {
        return costPerUnit * number;
    }

    public Cost next(Cost nextElement) {
        return nextElement;
    }

    @Override
    public String toString() {
        return "Material{" +
                "costPerUnit=" + costPerUnit +
                ", name='" + name + '\'' +
                '}';
    }
}
