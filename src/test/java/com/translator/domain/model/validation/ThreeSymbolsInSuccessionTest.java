package com.translator.domain.model.validation;

import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Test;

import java.util.Arrays;

import static com.translator.domain.model.numeral.RomanNumeral.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ThreeSymbolsInSuccessionTest {

    @Test public void
    numeralsValid_WhenAllowableSymbolRepeatedNoMoreThanThreeTimes() {
        assertValid(I,I,I);
        assertValid(X,X,X);
        assertValid(C,C,C);
        assertValid(M,M,M);
        assertValid(X,X,X,I,X);
        assertValid(M,M,M,C,C,C,X,X,X,I,I,I);
        assertValid(M,M,M,C,C,X,C,I,I,I);
    }

    @Test public void
    numeralsInvalid_AsAllowableSymbolRepeatedMoreThanThreeTimes() {
        assertInvalid(I,I,I,I);
        assertInvalid(X,I,I,I,I);
        assertInvalid(X,X,X,X);
        assertInvalid(X,X,X,X,V);
        assertInvalid(C,C,C,C);
        assertInvalid(M,M,M,M);
    }

    private void assertValid(RomanNumeral... numerals) {
        assertValid(true, "Checking Roman numerals " + Arrays.toString(numerals) + " are ok to be repeated max 3 times in succession", numerals);
    }

    private void assertInvalid(RomanNumeral... numerals) {
        assertValid(false, "Checking Roman numerals " + Arrays.toString(numerals) + " are not allow to be repeated more than 3 times in succession", numerals);
    }


    private void assertValid(boolean expected, String failureMessage, RomanNumeral... numerals) {
        ThreeSymbolsInSuccession threeSymbolsInSuccession = new ThreeSymbolsInSuccession();

        assertThat(failureMessage, threeSymbolsInSuccession.validate(asList(numerals)), is(expected));
    }
}
