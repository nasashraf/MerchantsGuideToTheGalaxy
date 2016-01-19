package com.translator.application;

import com.translator.application.test.doubles.CalculatorSpy;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.translator.domain.model.calculator.Credits.credits;
import static com.translator.domain.model.material.Material.aMaterial;
import static com.translator.domain.model.numeral.RomanNumeral.I;
import static com.translator.domain.model.numeral.RomanNumeral.V;
import static com.translator.domain.model.numeral.RomanNumeralAmount.aRomanNumeralAmount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IntergalacticTranslatorTest {

    private Map<String, RomanNumeral> intergalacticToRoman;
    private Map<String, Material> materialsByName;
    private IntergalacticTranslator intergalacticTranslator;
    private CalculatorSpy calculatorSpy;

    @Before
    public void createSUT() {
        intergalacticToRoman = new HashMap<String, RomanNumeral>();
        materialsByName = new HashMap<String, Material>();
        intergalacticTranslator = new IntergalacticTranslator(intergalacticToRoman, materialsByName);
        calculatorSpy = new CalculatorSpy();
        intergalacticTranslator.setCalculator(calculatorSpy);
    }

    @Test(expected = IntergalacticTranslator.TranslationException.class)
    public void noAnswer_WhenNoQuestionAsked() {
        String answer = intergalacticTranslator.translate("");

        assertThat(answer, is("I have no idea what you are talking about"));
    }

    @Test public void
    answerGiven_WhenSingleIntergalacticQuantityForMaterial() {
        intergalacticToRoman.put("glob", I);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        calculatorSpy.setCreditsAmount(credits(10.0));

        String answer = intergalacticTranslator.translate("how many Credits is glob Silver ?");

        assertThat(answer, is("glob Silver is 10.0 Credits"));
        assertThat(calculatorSpy.romanNumeralAmountCalledWith, is(aRomanNumeralAmount(I)));
        assertThat(calculatorSpy.materialCalledWith, is(Material.aMaterial("Silver", credits(10.0))));
    }

    @Test public void
    answerGiven_WhenMultipleIntergalacticQuantitiesForMaterial() {
        intergalacticToRoman.put("glob", I);
        intergalacticToRoman.put("prok", V);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        calculatorSpy.setCreditsAmount(credits(60.0));

        String answer = intergalacticTranslator.translate("how many Credits is prok glob Silver ?");

        assertThat(answer, is("prok glob Silver is 60.0 Credits"));
        assertThat(calculatorSpy.romanNumeralAmountCalledWith, is(aRomanNumeralAmount(V,I)));
        assertThat(calculatorSpy.materialCalledWith, is(Material.aMaterial("Silver", credits(10.0))));
    }


    @Test public void
    answerGiven_WhenIntergalacticQuantitiesAndMaterialExist() {
        intergalacticToRoman.put("glob", I);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        calculatorSpy.setCreditsAmount(credits(10.0));

        String answer = intergalacticTranslator.translate("how many Credits is glob Silver ?");

        assertThat(answer, is("glob Silver is 10.0 Credits"));
        assertThat(calculatorSpy.romanNumeralAmountCalledWith, is(aRomanNumeralAmount(I)));
        assertThat(calculatorSpy.materialCalledWith, is(Material.aMaterial("Silver", credits(10.0))));
    }

    @Test(expected = IntergalacticTranslator.TranslationException.class)
    public void translationException_WhenMaterialDoesNotExist() {
        intergalacticToRoman.put("glob", I);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        intergalacticTranslator.translate("how many Credits is glob Copper ?");

    }

    @Test(expected = IntergalacticTranslator.TranslationException.class)
    public void translationException_WhenRomanNumeralForIntergalacticQuantityDoesNotExist() {
        intergalacticToRoman.put("prok", V);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        intergalacticTranslator.translate("how many Credits is glob Silver ?");
    }


    @Test(expected = IntergalacticTranslator.TranslationException.class)
    public void translationException_WhenBadlyFormattedQuestion() {
        intergalacticToRoman.put("glob", I);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        calculatorSpy.setCreditsAmount(credits(10.0));

        intergalacticTranslator.translate("how many Credits is globSilver ?");
    }

}
