package com.translator.application;

import com.translator.application.test.doubles.CalculatorSpy;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Before;
import org.junit.Test;

import static com.translator.domain.model.calculator.Credits.credits;
import static com.translator.domain.model.material.Material.aMaterial;
import static com.translator.domain.model.numeral.RomanNumeral.I;
import static com.translator.domain.model.numeral.RomanNumeralAmount.aRomanNumeralAmount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IntergalacticTranslatorTest {

    private MapBuilder<String, RomanNumeral> intergalacticToRoman;
    private MapBuilder<String, Material> materialsByName;

    @Before
    public void createSUT() {
        intergalacticToRoman = MapBuilder.<String, RomanNumeral>aMapBuilder();
        materialsByName = MapBuilder.<String, Material>aMapBuilder();
    }

    @Test public void
    noAnswer_WhenNoQuestionAsked() {
        IntergalacticTranslator intergalacticTranslator = new IntergalacticTranslator(intergalacticToRoman.build(), materialsByName.build());

        String answer = intergalacticTranslator.translate("");

        assertThat(answer, is("I have no idea what you are talking about"));
    }

    @Test public void
    answerGiven_WhenIntergalacticQuantitiesAndMaterialExist() {
        intergalacticToRoman.put("glob", I);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        CalculatorSpy calculatorSpy = new CalculatorSpy();
        calculatorSpy.setCreditsAmount(credits(10.0));

        IntergalacticTranslator intergalacticTranslator = new IntergalacticTranslator(intergalacticToRoman.build(), materialsByName.build());
        intergalacticTranslator.setCalculator(calculatorSpy);

        String answer = intergalacticTranslator.translate("how many Credits is glob Silver ?");

        assertThat(answer, is("glob Silver is 10.0 Credits"));
        assertThat(calculatorSpy.romanNumeralAmountCalledWith, is(aRomanNumeralAmount(I)));
        assertThat(calculatorSpy.materialCalledWith, is(Material.aMaterial("Silver", credits(10.0))));
    }



}
