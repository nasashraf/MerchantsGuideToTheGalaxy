package com.translator.domain.model.material;

import com.translator.domain.model.calculator.Credits;
import com.translator.domain.model.numeral.Material;
import com.translator.domain.model.numeral.MultiplyMaterialCost;
import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.translator.domain.model.calculator.Credits.credits;
import static com.translator.domain.model.numeral.Material.aMaterial;
import static com.translator.domain.model.material.MaterialPricePerUnitTest.MaterialBuilder.aMaterialCalled;
import static com.translator.domain.model.numeral.RomanNumeral.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MaterialPricePerUnitTest {

    @Test public void
    pricePerUnitExpressedInCredits_WhenAmountOfMaterialIsOne() {
        assertThat(aMaterialCalled("Silver").ofQuantity(I).andTotalWorthOf(credits(34.0)), is(aMaterial("Silver", credits(34.0), new MultiplyMaterialCost())));
        assertThat(aMaterialCalled("Silver").ofQuantity(I,I).andTotalWorthOf(credits(34.0)), is(aMaterial("Silver", credits(17.0), new MultiplyMaterialCost())));
        assertThat(aMaterialCalled("Silver").ofQuantity(I,I).andTotalWorthOf(credits(17.0)), is(aMaterial("Silver", credits(8.5), new MultiplyMaterialCost())));
        assertThat(aMaterialCalled("Gold").ofQuantity(I,I,I).andTotalWorthOf(credits(21.0)), is(aMaterial("Gold", credits(7.0), new MultiplyMaterialCost())));
        assertThat(aMaterialCalled("Gold").ofQuantity(I,V).andTotalWorthOf(credits(32.0)), is(aMaterial("Gold", credits(8.0), new MultiplyMaterialCost())));
        assertThat(aMaterialCalled("Platinum").ofQuantity(V).andTotalWorthOf(credits(100.0)), is(aMaterial("Platinum", credits(20.0), new MultiplyMaterialCost())));
        assertThat(aMaterialCalled("Sand").ofQuantity(V,I).andTotalWorthOf(credits(12.0)), is(aMaterial("Sand", credits(2.0), new MultiplyMaterialCost())));
        assertThat(aMaterialCalled("Copper").ofQuantity(X).andTotalWorthOf(credits(50.0)), is(aMaterial("Copper", credits(5.0), new MultiplyMaterialCost())));
        assertThat(aMaterialCalled("Copper").ofQuantity(L).andTotalWorthOf(credits(100.0)), is(aMaterial("Copper", credits(2.0), new MultiplyMaterialCost())));
        assertThat(aMaterialCalled("Copper").ofQuantity(C).andTotalWorthOf(credits(1000.0)), is(aMaterial("Copper", credits(10.0), new MultiplyMaterialCost())));
        assertThat(aMaterialCalled("Copper").ofQuantity(D).andTotalWorthOf(credits(1000.0)), is(aMaterial("Copper", credits(2.0), new MultiplyMaterialCost())));
        assertThat(aMaterialCalled("Copper").ofQuantity(M).andTotalWorthOf(credits(5000.0)), is(aMaterial("Copper", credits(5.0), new MultiplyMaterialCost())));

        assertThat(aMaterialCalled("Copper").ofQuantity(M,D,X,C,I,V).andTotalWorthOf(credits(1594.0)), is(aMaterial("Copper", credits(1.0), new MultiplyMaterialCost())));
    }


    public static class MaterialBuilder {
        private String materialName;
        private List<RomanNumeral> romanNumerals;

        public MaterialBuilder(String materialName) {
            this.materialName = materialName;
        }

        public static MaterialBuilder aMaterialCalled(String materialName) {
            return new MaterialBuilder(materialName);
        }

        public MaterialBuilder ofQuantity(RomanNumeral... romanNumeral) {
            this.romanNumerals = Arrays.asList(romanNumeral);
            return this;
        }

        public Material andTotalWorthOf(Credits credits) {
            MaterialPricePerUnit materialPricePerUnit = new MaterialPricePerUnit();

            return materialPricePerUnit.material(romanNumerals, materialName, credits);
        }
    }
}
