package com.translator.domain.model.validation;

import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Test;

import java.util.Arrays;

import static com.translator.domain.model.numeral.RomanNumeral.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SmallSymbolPrecedingLargeSymbolTest {

    @Test
    public void
    numeralsInvalid_WhenMoreThanOneSmallValueSymbolPrecedesLargeSymbol() {
        assertInvalid(I,I,V);
        assertInvalid(I,I,X);
        assertInvalid(X,X,L);
        assertInvalid(X,X,C);
        assertInvalid(C,C,D);
        assertInvalid(C,C,M);

        assertInvalid(I,V,X);
        assertInvalid(X,I,I,V);
        assertInvalid(M,D,C,X,X,L);
    }

    private void assertInvalid(RomanNumeral... numerals) {
        SmallSymbolPrecedingLargeSymbol smallSymbolPrecedingLargeSymbol = new SmallSymbolPrecedingLargeSymbol();

        assertThat("Checking Roman numerals " + Arrays.toString(numerals) + " does not contain more than one small symbol preceding a large one", smallSymbolPrecedingLargeSymbol.validate(asList(numerals)), is(false));
    }
}
