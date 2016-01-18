package com.translator.domain.model.validation;

import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Test;

import java.util.Arrays;

import static com.translator.domain.model.numeral.RomanNumeral.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NonRepeatableSymbolTest {

    @Test
    public void
    numeralsValid_WhenNoNonRepeatableSymbols() {
        assertSymbolCanBeRepeated(I,I);
        assertSymbolCanBeRepeated(X,X);
        assertSymbolCanBeRepeated(C,C);
        assertSymbolCanBeRepeated(M,M);
    }

    @Test
    public void
    numeralsInvalid_WhenNonRepeatableSymbolsPresent() {
        assertSymbolCannotRepeated(V,V);
        assertSymbolCannotRepeated(L,L);
        assertSymbolCannotRepeated(D,D);
    }

    private void assertSymbolCanBeRepeated(RomanNumeral... numerals) {
        assertValid(true, "Checking Roman numerals " + Arrays.toString(numerals) + " are ok to be repeated", numerals);
    }

    private void assertSymbolCannotRepeated(RomanNumeral... numerals) {
        assertValid(false, "Checking Roman numerals " + Arrays.toString(numerals) + " are not allow to be repeated ", numerals);
    }

    private void assertValid(boolean expected, String failureMessage, RomanNumeral... numerals) {
        NonRepeatableSymbol nonRepeatableSymbol = new NonRepeatableSymbol();

        assertThat(failureMessage, nonRepeatableSymbol.validate(asList(numerals)), is(expected));
    }
}
