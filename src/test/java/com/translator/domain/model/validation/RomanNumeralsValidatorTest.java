package com.translator.domain.model.validation;

import org.junit.Before;
import org.junit.Test;

import static com.translator.domain.model.numeral.RomanNumeral.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RomanNumeralsValidatorTest {

    private RomanNumeralValidator romanNumeralValidator;

    @Before
    public void setUp() {
        romanNumeralValidator = new RomanNumeralValidator();
    }

    @Test public void
    numeralsAreValid_WhenInDescendingOrder() {
        assertThat(romanNumeralValidator.validate(asList(M,D,C,L,X,V,I)), is(true));
    }

    @Test public void
    numeralsAreInvalid_WhenNotInDescendingOrder() {
        assertThat(romanNumeralValidator.validate(asList(M,D,C,L,V,X)), is(false));
    }

    @Test public void
    numeralsAreValid_WhenNonRepeatableSymbolsNotPresent() {
        assertThat(romanNumeralValidator.validate(asList(I,I)), is(true));
    }

    @Test public void
    numeralsAreInvalid_WhenNonRepeatableSymbolsPresent() {
        assertThat(romanNumeralValidator.validate(asList(V,V)), is(false));
    }

    @Test public void
    numeralsAreInvalid_WhenThereAreSucceedingSmallSymbolsPrecedingALargerOne() {
        assertThat(romanNumeralValidator.validate(asList(M,D,C,X,X,L)), is(false));
    }

    @Test public void
    numeralsAreInvalid_WhenSubtractionPairsAreRepeated() {
        assertThat(romanNumeralValidator.validate(asList(M,X,L,X,L)), is(false));
    }


    @Test public void
    numeralsAreValid_WhenAllowableSymbolRepeatedNoMoreThanThreeTimes() {
        assertThat(romanNumeralValidator.validate(asList(X,X,X,I,X)), is(true));
    }

    @Test public void
    numeralsAreInvalid_WhenAllowableSymbolRepeatedMoreThanThreeTimes() {
        assertThat(romanNumeralValidator.validate(asList(X,X,X,X,V)), is(false));
    }
}
