package com.translator.application;

import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.translator.domain.model.numeral.RomanNumeral.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IntergalacticToRomanAdapterTest {

    private IntergalacticToRomanAdapter intergalacticToRomanAdapter;

    @Before
    public void createSUT() {
        intergalacticToRomanAdapter = new IntergalacticToRomanAdapter();
    }

    @Test public void
    noTranslation_WhenTranslationTextEmpty() {
        Map<String, RomanNumeral> intergalacticToRoman = intergalacticToRomanAdapter.convert(new ArrayList<String>());

        assertThat(intergalacticToRoman.isEmpty(), is(true));
    }

    @Test public void
    translateIntergalacticToRomanOne() {
        Map<String, RomanNumeral> intergalacticToRoman = intergalacticToRomanAdapter.convert(translationBuilder("glob is I"));

        assertThat(intergalacticToRoman.get("glob"), is(I));
    }

    @Test public void
    translateIntergalacticToRomanFive() {
        Map<String, RomanNumeral> intergalacticToRoman = intergalacticToRomanAdapter.convert(translationBuilder("prok is V"));

        assertThat(intergalacticToRoman.get("prok"), is(V));
    }

    @Test public void
    translateIntergalacticToRomanTen() {
        Map<String, RomanNumeral> intergalacticToRoman = intergalacticToRomanAdapter.convert(translationBuilder("pish is X"));

        assertThat(intergalacticToRoman.get("pish"), is(X));
    }

    @Test public void
    translateIntergalacticToRomanFifty() {
        Map<String, RomanNumeral> intergalacticToRoman = intergalacticToRomanAdapter.convert(translationBuilder("teji is L"));

        assertThat(intergalacticToRoman.get("teji"), is(L));
    }

    @Test public void
    translateIntergalacticToRomanOneHundred() {
        Map<String, RomanNumeral> intergalacticToRoman = intergalacticToRomanAdapter.convert(translationBuilder("moji is C"));

        assertThat(intergalacticToRoman.get("moji"), is(C));
    }


    @Test public void
    translateIntergalacticToRomanFiveHundred() {
        Map<String, RomanNumeral> intergalacticToRoman = intergalacticToRomanAdapter.convert(translationBuilder("chewy is D"));

        assertThat(intergalacticToRoman.get("chewy"), is(D));
    }

    @Test public void
    translateIntergalacticToRomanOneThousand() {
        Map<String, RomanNumeral> intergalacticToRoman = intergalacticToRomanAdapter.convert(translationBuilder("leccy is M"));

        assertThat(intergalacticToRoman.get("leccy"), is(M));
    }


    @Test public void
    translateIntergalacticToRomanForMultipleTranslationEntries() {
        List<String> translations = translationBuilder("glob is I"
                                                      ,"prok is V"
                                                      ,"pish is X"
                                                      ,"teji is L"
                                                      ,"moji is C"
                                                      ,"chewy is D"
                                                      ,"leccy is M");


        Map<String, RomanNumeral> intergalacticToRoman = intergalacticToRomanAdapter.convert(translations);

        assertThat(intergalacticToRoman.get("glob"), is(I));
        assertThat(intergalacticToRoman.get("prok"), is(V));
        assertThat(intergalacticToRoman.get("pish"), is(X));
        assertThat(intergalacticToRoman.get("teji"), is(L));
        assertThat(intergalacticToRoman.get("moji"), is(C));
        assertThat(intergalacticToRoman.get("chewy"), is(D));
        assertThat(intergalacticToRoman.get("leccy"), is(M));
    }

    @Test public void
    translate_WhenLeadingAndTrailingSpaces() {
        Map<String, RomanNumeral> intergalacticToRoman = intergalacticToRomanAdapter.convert(translationBuilder("     leccy     is      M     "));

        assertThat(intergalacticToRoman.get("leccy"), is(M));
    }

    @Test public void
    exceptionThrown_WhenBadTranslationText() {
        assertTranslationExptionThrownWhenBadText("leccy isM");
        assertTranslationExptionThrownWhenBadText("leccyis M");
        assertTranslationExptionThrownWhenBadText("leccy is is M");
        assertTranslationExptionThrownWhenBadText("leccy is M is X");
    }


    @Test(expected = IntergalacticToRomanAdapter.TranslationException.class)
    public void exceptionThrown_WhenNoTranslationFound() {
        String badText = "leccy is Z";

        intergalacticToRomanAdapter.convert(translationBuilder(badText));
    }


    private List<String> translationBuilder(String... translations) {
        return Arrays.asList(translations);
    }


    private void assertTranslationExptionThrownWhenBadText(String badText) {
        IntergalacticToRomanAdapter intergalacticToRomanAdapter = new IntergalacticToRomanAdapter();

        boolean excptionCaught = false;

        try {
            intergalacticToRomanAdapter.convert(translationBuilder(badText));
        } catch(IntergalacticToRomanAdapter.TranslationException trans) {
            excptionCaught = true;
        }

        assertThat(excptionCaught, is(true));
    }

}
