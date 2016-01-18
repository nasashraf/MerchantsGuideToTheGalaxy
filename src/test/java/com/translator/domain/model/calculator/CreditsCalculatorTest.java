package com.translator.domain.model.calculator;

import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeralAmount;
import org.hamcrest.Matcher;
import org.junit.Test;

import static com.translator.domain.model.calculator.Credits.credits;
import static com.translator.domain.model.calculator.CreditsCalculatorTest.CreditCalculatorBuilder.aMaterialOfValue;
import static com.translator.domain.model.numeral.RomanNumeral.*;
import static com.translator.domain.model.numeral.RomanNumeralAmount.aRomanNumeralAmount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreditsCalculatorTest {

    @Test public void
    worthOfMaterialIsCalculatedCorrectly_ForDifferentAmountsExpressedInRomanNUmerals() {
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount()), isWorth(credits(0.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(I)), isWorth(credits(100.0)));
        assertThat(aMaterialOfValue(credits(200.0)).forAmountOf(aRomanNumeralAmount(I)), isWorth(credits(200.0)));
        assertThat(aMaterialOfValue(credits(100.5)).forAmountOf(aRomanNumeralAmount(I)), isWorth(credits(100.5)));

        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(I,I)), isWorth(credits(200.0)));
        assertThat(aMaterialOfValue(credits(100.5)).forAmountOf(aRomanNumeralAmount(I,I)), isWorth(credits(201.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(I,I,I)), isWorth(credits(300.0)));

        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(V)), isWorth(credits(500.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(V,I)), isWorth(credits(600.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(V,I,I)), isWorth(credits(700.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(V,I,I,I)), isWorth(credits(800.0)));

        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(X)), isWorth(credits(1000.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(L)), isWorth(credits(5000.0)));
        assertThat(aMaterialOfValue(credits(50.0)).forAmountOf(aRomanNumeralAmount(C)), isWorth(credits(5000.0)));
        assertThat(aMaterialOfValue(credits(30.0)).forAmountOf(aRomanNumeralAmount(D)), isWorth(credits(15000.0)));
        assertThat(aMaterialOfValue(credits(2.0)).forAmountOf(aRomanNumeralAmount(M)), isWorth(credits(2000.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(M,D,C,L,X,V,I)), isWorth(credits(166600.0)));

        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(I,V)), isWorth(credits(400.0)));
        assertThat(aMaterialOfValue(credits(10.0)).forAmountOf(aRomanNumeralAmount(X,I,V)), is(credits(140.0)));

        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(I,X)), isWorth(credits(900.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(X,I,X)), isWorth(credits(1900.0)));

        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(X,L)), isWorth(credits(4000.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(X,L,V)), isWorth(credits(4500.0)));

        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(X,C)), isWorth(credits(9000.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(C,X,C)), isWorth(credits(19000.0)));

        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(C,D)), isWorth(credits(40000.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(M,C,D)), isWorth(credits(140000.0)));

        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(C,M)), isWorth(credits(90000.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(M,C,M)), isWorth(credits(190000.0)));

        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(C,D,L,X,X,X,I,X)), isWorth(credits(48900.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(C,M,X,C,I,X)), isWorth(credits(99900.0)));
        assertThat(aMaterialOfValue(credits(100.0)).forAmountOf(aRomanNumeralAmount(M,M,M,M,C,M,X,C,I,X)), isWorth(credits(499900.0)));


    }

    private Matcher<Credits> isWorth(Credits credits) {
        return is(credits);
    }

    public static class CreditCalculatorBuilder {

        private Material material;

        public CreditCalculatorBuilder(Material material) {
            this.material = material;
        }

        public static CreditCalculatorBuilder aMaterialOfValue(Credits credits) {
            return new CreditCalculatorBuilder(new Material("any", credits) );
        }

        public Credits forAmountOf(RomanNumeralAmount romanNumeralAmount) {
            CreditsCalculator creditsCalculator = new CreditsCalculator();

            Credits calculate = creditsCalculator.calculate(romanNumeralAmount, material);
            return calculate;
        }

    }



}
