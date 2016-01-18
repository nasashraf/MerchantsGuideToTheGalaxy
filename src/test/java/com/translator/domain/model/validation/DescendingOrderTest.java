package com.translator.domain.model.validation;

import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Test;

import java.util.Arrays;

import static com.translator.domain.model.numeral.RomanNumeral.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DescendingOrderTest {

    @Test public void
    numeralsValid_WhenInDescendingOrderAndContainNoSubtractionCombinations() {
        assertNumeralsOrderValid(I);
        assertNumeralsOrderValid(I,I);
        assertNumeralsOrderValid(V,I);
        assertNumeralsOrderValid(X,V);
        assertNumeralsOrderValid(L,X);
        assertNumeralsOrderValid(C,L);
        assertNumeralsOrderValid(D,C);
        assertNumeralsOrderValid(M,D);
        assertNumeralsOrderValid(M,D,C,L,X,V,I);
    }

    @Test public void
    numeralsInvalid_WhenInDescendingOrderAndContainNoSubtractionCombinations() {
        assertNumeralsOrderInvalid(V,X);
        assertNumeralsOrderInvalid(L,C);
        assertNumeralsOrderInvalid(D,M);
        assertNumeralsOrderInvalid(M,D,C,L,V,X);
    }

    @Test public void
    numeralsValid_WhenInDescendingOrderAndContainSubtractionCombinations() {
        assertNumeralsOrderValid(I,V);
        assertNumeralsOrderValid(I,X);
        assertNumeralsOrderValid(X,L);
        assertNumeralsOrderValid(X,C);
        assertNumeralsOrderValid(C,D);
        assertNumeralsOrderValid(C,M);
    }

    private void assertNumeralsOrderValid(RomanNumeral... numerals) {
        assertValid(true, "Checking Roman numerals " + Arrays.toString(numerals) + " are in valid order", numerals);
    }

    private void assertNumeralsOrderInvalid(RomanNumeral... numerals) {
        assertValid(false, "Checking Subtraction Roman numerals " + Arrays.toString(numerals) + " are considered as valid order", numerals);
    }

    private void assertValid(boolean expected, String failureMessage, RomanNumeral... numerals) {
        DescendingOrder descendingOrder = new DescendingOrder();

        assertThat(failureMessage, descendingOrder.validate(asList(numerals)), is(expected));
    }

}
