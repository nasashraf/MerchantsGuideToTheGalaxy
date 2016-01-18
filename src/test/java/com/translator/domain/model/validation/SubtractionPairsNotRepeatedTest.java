package com.translator.domain.model.validation;

import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Test;

import java.util.Arrays;

import static com.translator.domain.model.numeral.RomanNumeral.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SubtractionPairsNotRepeatedTest {

    @Test
    public void
    numeralsInvalid_WhenSubtractionPairsAppearMoreThanOnce() {
        assertInvalid(I,V,I,V);
        assertInvalid(I,X,I,X);
        assertInvalid(X,L,X,L);
        assertInvalid(X,C,X,C);
        assertInvalid(C,D,C,D);
        assertInvalid(C,M,C,M);

        assertInvalid(M,X,L,X,L);
    }

    private void assertInvalid(RomanNumeral... numerals) {
        SubtractionPairsNotRepeated subtractionPairsNotRepeated = new SubtractionPairsNotRepeated();

        assertThat("Checking Roman numerals " + Arrays.toString(numerals) + " does not contain the same subtraction pairs more than once", subtractionPairsNotRepeated.validate(asList(numerals)), is(false));
    }
}
