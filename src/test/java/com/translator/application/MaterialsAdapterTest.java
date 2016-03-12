package com.translator.application;

import com.translator.application.test.doubles.MaterialPricePerUnitSpy;
import com.translator.application.test.doubles.ValidatorSpy;
import com.translator.domain.model.numeral.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.translator.domain.model.calculator.Credits.credits;
import static com.translator.domain.model.numeral.Material.aMaterial;
import static com.translator.domain.model.numeral.RomanNumeral.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class MaterialsAdapterTest {

    private static final List<RomanNumeral> ROMAN_ONE = asList(I);
    private static final List<RomanNumeral> I_I = asList(I, I);
    private static final List<RomanNumeral> L_X_I_V = asList(L,X,I,V);
    private static final String SILVER = "Silver";
    private static final String GOLD = "Gold";
    private static final String TOTAL_COST = "34";
    private static final String ONE_HUNDRED_TWENTY_EIGHT = "128";
    private static final String TWO_HUNDRED = "200";

    private static final Map<String, RomanNumeral> INTERGALACTIC_TO_ROMAN = new HashMap<String, RomanNumeral>() {{
        put("glob", I);
        put("prok", V);
        put("pish", X);
        put("teji", L);
    }};

    private MaterialsAdapter materialsAdapter;
    private MaterialPricePerUnitSpy materialPricePerUnitSpy;
    private ValidatorSpy validatorSpy;

    private List<String> materialCosts;

    @Before
    public void createSUT() {
        materialsAdapter = new MaterialsAdapter(INTERGALACTIC_TO_ROMAN);
        materialPricePerUnitSpy = new MaterialPricePerUnitSpy();
        validatorSpy = new ValidatorSpy();
        materialsAdapter.setMaterialFactory(materialPricePerUnitSpy);
        materialsAdapter.setValidator(validatorSpy);
        materialCosts = new ArrayList<String>();
    }

    @Test public void
    correctMaterialCreated_WhenASingleQuantityIsSpecified() {
        String materialCostText = MaterialCostTextBuilder.aMaterialCostTextBuilder()
                .withQuantity("glob")
                .withMaterialName(SILVER)
                .withCreditsAmount(parseInt(TOTAL_COST))
                .build();

        materialCosts.add(materialCostText);

        final Material materialStub = aMaterial(SILVER, credits(34.0));
        Map<String,Material> responseStub = new HashMap<String, Material>() {{
            put(SILVER, materialStub);

        }};

        materialPricePerUnitSpy.setMaterials(responseStub);
        validatorSpy.setValidationResult(true);

        Map<String, Material> materials = materialsAdapter.createMaterialBasedOnPricePerUnit(materialCosts);

        assertThat(materials.size(), is(1));
        assertThat(materials.get(SILVER), is(materialStub));
        assertThat(materialPricePerUnitSpy.romanNumerals(), equalTo(asList(ROMAN_ONE)));
        assertThat(materialPricePerUnitSpy.materialName(), equalTo(asList(SILVER)));
        assertThat(materialPricePerUnitSpy.cost(), equalTo(asList(credits(34.0))));
    }

    @Test public void
    correctMaterialCreated_WhenAQuantityOfTwoInCorrectOrderIsSpecified() {
        String materialCostText = MaterialCostTextBuilder.aMaterialCostTextBuilder()
                                            .withQuantity("glob")
                                            .withQuantity("glob")
                                            .withMaterialName(SILVER)
                                            .withCreditsAmount(parseInt(TOTAL_COST))
                                            .build();

        materialCosts.add(materialCostText);

        final Material materialStub = aMaterial(SILVER, credits(17.0));
        Map<String,Material> responseStub = new HashMap<String, Material>() {{
            put(SILVER, materialStub);

        }};

        materialPricePerUnitSpy.setMaterials(responseStub);
        validatorSpy.setValidationResult(true);

        Map<String, Material> material = materialsAdapter.createMaterialBasedOnPricePerUnit(materialCosts);

        assertThat(material.size(), is(1));
        assertThat(material.get(SILVER), is(materialStub));
        assertThat(materialPricePerUnitSpy.romanNumerals(), equalTo(asList(I_I)));
        assertThat(materialPricePerUnitSpy.materialName(), equalTo(asList(SILVER)));
        assertThat(materialPricePerUnitSpy.cost(), equalTo(asList(credits(parseDouble(TOTAL_COST)))));
    }

    @Test public void
    correctMaterialCreated_WhenALargeQuantityInOrderIsSpecified() {
        String materialCostText = MaterialCostTextBuilder.aMaterialCostTextBuilder()
                .withQuantity("teji")
                .withQuantity("pish")
                .withQuantity("glob")
                .withQuantity("prok")
                .withMaterialName(GOLD)
                .withCreditsAmount(parseInt(ONE_HUNDRED_TWENTY_EIGHT))
                .build();
        materialCosts.add(materialCostText);

        final Material materialStub = aMaterial(GOLD, credits(2.0));
        Map<String,Material> responseStub = new HashMap<String, Material>() {{
            put(GOLD, materialStub);

        }};

        materialPricePerUnitSpy.setMaterials(responseStub);
        validatorSpy.setValidationResult(true);

        Map<String, Material> material = materialsAdapter.createMaterialBasedOnPricePerUnit(materialCosts);

        assertThat(material.size(), is(1));
        assertThat(material.get(GOLD), is(materialStub));
        assertThat(materialPricePerUnitSpy.romanNumerals(), equalTo(asList(L_X_I_V)));
        assertThat(materialPricePerUnitSpy.materialName(), equalTo(asList(GOLD)));
        assertThat(materialPricePerUnitSpy.cost(), equalTo(asList(credits(parseDouble(ONE_HUNDRED_TWENTY_EIGHT)))));
    }

    @Test public void
    correctMaterialsCreated_WhenMultipleMaterialCostsSpecified() {
        String materialCostTextGold = MaterialCostTextBuilder.aMaterialCostTextBuilder()
                .withQuantity("teji")
                .withQuantity("pish")
                .withQuantity("glob")
                .withQuantity("prok")
                .withMaterialName(GOLD)
                .withCreditsAmount(parseInt(ONE_HUNDRED_TWENTY_EIGHT))
                .build();

        String materialCostTextSilver = MaterialCostTextBuilder.aMaterialCostTextBuilder()
                .withQuantity("glob")
                .withQuantity("glob")
                .withMaterialName(SILVER)
                .withCreditsAmount(parseInt(TWO_HUNDRED))
                .build();

        materialCosts.add(materialCostTextGold);
        materialCosts.add(materialCostTextSilver);

        final Material goldStub = aMaterial(GOLD, credits(2.0));
        final Material silverStub = aMaterial(SILVER, credits(2.0));
        Map<String,Material> responseStub = new HashMap<String, Material>() {{
            put(GOLD, goldStub);
            put(SILVER, silverStub);

        }};

        materialPricePerUnitSpy.setMaterials(responseStub);
        validatorSpy.setValidationResult(true);

        Map<String, Material> material = materialsAdapter.createMaterialBasedOnPricePerUnit(materialCosts);

        assertThat(material.size(), is(2));
        assertThat(material.get(GOLD), is(goldStub));
        assertThat(material.get(SILVER), is(silverStub));
        assertThat(materialPricePerUnitSpy.romanNumerals(), equalTo(asList(L_X_I_V, I_I)));
        assertThat(materialPricePerUnitSpy.materialName(), equalTo(asList(GOLD, SILVER)));
        assertThat(materialPricePerUnitSpy.cost(), equalTo(asList(credits(parseDouble(ONE_HUNDRED_TWENTY_EIGHT)),credits(parseDouble(TWO_HUNDRED)))));
    }

    @Test(expected = MaterialsAdapter.MaterialsAdapterException.class)
    public void materialsAdapterException_WhenQuantitySpecifiedHasInvalidOrder() {
        String materialCostText = MaterialCostTextBuilder.aMaterialCostTextBuilder()
                .withQuantity("glob")
                .withQuantity("prok")
                .withQuantity("pish")
                .withMaterialName(GOLD)
                .withCreditsAmount(parseInt(ONE_HUNDRED_TWENTY_EIGHT))
                .build();
        materialCosts.add(materialCostText);

        final Material materialStub = aMaterial(GOLD, credits(2.0));
        Map<String,Material> responseStub = new HashMap<String, Material>() {{
            put(GOLD, materialStub);

        }};
        materialPricePerUnitSpy.setMaterials(responseStub);
        validatorSpy.setValidationResult(false);

        materialsAdapter.createMaterialBasedOnPricePerUnit(materialCosts);
    }

    private static class MaterialCostTextBuilder {
        private List<String> intergalacticQuantity;
        private String materialName;
        private int creditsAmount;

        public MaterialCostTextBuilder() {
            intergalacticQuantity = new ArrayList<String>();
        }

        static MaterialCostTextBuilder aMaterialCostTextBuilder() {
            return new MaterialCostTextBuilder();
        }

        MaterialCostTextBuilder withQuantity(String quantity) {
            intergalacticQuantity.add(quantity);
            return this;
        }

        MaterialCostTextBuilder withMaterialName(String name) {
            this.materialName = name;
            return this;
        }

        MaterialCostTextBuilder withCreditsAmount(int creditsAmount) {
            this.creditsAmount = creditsAmount;
            return this;
        }

        String build() {
            StringBuilder builder = new StringBuilder();

            for (String quantity : intergalacticQuantity) {
                builder.append(quantity);
                builder.append(" ");
            }

            builder.append(materialName).append(" is ").append(creditsAmount).append(" Credits");

            return builder.toString();
        }
    }


}
