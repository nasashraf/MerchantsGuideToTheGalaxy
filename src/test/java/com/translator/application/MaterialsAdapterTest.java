package com.translator.application;

import com.translator.application.test.doubles.MaterialPricePerUnitSpy;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.translator.domain.model.calculator.Credits.credits;
import static com.translator.domain.model.material.Material.aMaterial;
import static com.translator.domain.model.numeral.RomanNumeral.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class MaterialsAdapterTest {

    public static final List<RomanNumeral> ROMAN_ONE = asList(I);
    private static final List<RomanNumeral> I_I = asList(I, I);
    private static final List<RomanNumeral> L_X_I_V = asList(L,X,I,V);
    private static final String SILVER = "Silver";
    private static final String GOLD = "Gold";
    private static final String TOTAL_COST = "34";
    private static final String ONE_HUNDRED_TWENTY_EIGHT = "128";

    private static final Map<String, RomanNumeral> INTERGALACTIC_TO_ROMAN = new HashMap<String, RomanNumeral>() {{
        put("glob", I);
        put("prok", V);
        put("pish", X);
        put("teji", L);
    }};


    private MaterialsAdapter materialsAdapter;

    @Before
    public void createSUT() {
        materialsAdapter = new MaterialsAdapter(INTERGALACTIC_TO_ROMAN);

    }

    @Test public void
    correctMaterialCreated_WhenASingleQuantityIsSpecified() {
        MaterialPricePerUnitSpy materialPricePerUnitSpy = new MaterialPricePerUnitSpy(aMaterial("Silver", credits(34.0)));
        materialsAdapter.setMaterialPricePerUnit(materialPricePerUnitSpy);

        Material material = materialsAdapter.createMaterialBasedOnPricePerUnit("glob Silver is 34 Credits");

        assertThat(material, is(aMaterial("Silver", credits(34.0))));

        assertThat(materialPricePerUnitSpy.romanNumerals(), equalTo(ROMAN_ONE));

        assertThat(materialPricePerUnitSpy.materialName(), equalTo("Silver"));
        assertThat(materialPricePerUnitSpy.cost(), equalTo(credits(34.0)));
    }

    @Test public void
    correctMaterialCreated_WhenAQuantityOfTwoIsSpecified() {
        String materialCostText = MaterialCostTextBuilder.aMaterialCostTextBuilder()
                                            .withQuantity("glob")
                                            .withQuantity("glob")
                                            .withMaterialName(SILVER)
                                            .withCreditsAmount(parseInt(TOTAL_COST))
                                            .build();

        Material responseStub = aMaterial(SILVER, credits(17.0));
        MaterialPricePerUnitSpy materialPricePerUnitSpy = new MaterialPricePerUnitSpy(responseStub);

        materialsAdapter.setMaterialPricePerUnit(materialPricePerUnitSpy);

        Material material = materialsAdapter.createMaterialBasedOnPricePerUnit(materialCostText);

        assertThat(material, is(responseStub));
        assertThat(materialPricePerUnitSpy.romanNumerals(), equalTo(I_I));
        assertThat(materialPricePerUnitSpy.materialName(), equalTo(SILVER));
        assertThat(materialPricePerUnitSpy.cost(), equalTo(credits(parseDouble(TOTAL_COST))));
    }

    @Test public void
    correctMaterialCreated_WhenALargeQuantityIsSpecified() {
        String materialCostText = MaterialCostTextBuilder.aMaterialCostTextBuilder()
                .withQuantity("teji")
                .withQuantity("pish")
                .withQuantity("glob")
                .withQuantity("prok")
                .withMaterialName(GOLD)
                .withCreditsAmount(parseInt(ONE_HUNDRED_TWENTY_EIGHT))
                .build();

        Material responseStub = aMaterial(GOLD, credits(2.0));
        MaterialPricePerUnitSpy materialPricePerUnitSpy = new MaterialPricePerUnitSpy(responseStub);

        materialsAdapter.setMaterialPricePerUnit(materialPricePerUnitSpy);

        Material material = materialsAdapter.createMaterialBasedOnPricePerUnit(materialCostText);

        assertThat(material, is(responseStub));
        assertThat(materialPricePerUnitSpy.romanNumerals(), equalTo(L_X_I_V));
        assertThat(materialPricePerUnitSpy.materialName(), equalTo(GOLD));
        assertThat(materialPricePerUnitSpy.cost(), equalTo(credits(parseDouble(ONE_HUNDRED_TWENTY_EIGHT))));
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
