package com.translator.application;

import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.domain.model.numeral.RomanNumeralCalculator;
import com.translator.domain.model.validation.RomanNumeralValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.translator.domain.model.credits.Credits.credits;
import static com.translator.domain.model.material.Material.aMaterial;
import static com.translator.domain.model.numeral.RomanNumeral.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IntergalacticWorthCalculationProcessorIntegrationTest {

    private Map<String, RomanNumeral> intergalacticToRoman;
    private Map<String, Material> materialsByName;
    private AnswerMaterialWorth answerMaterialWorth;

    @Before
    public void createSUT() {
        intergalacticToRoman = new HashMap<String, RomanNumeral>();
        materialsByName = new HashMap<String, Material>();

        QuantityParser quantityParser = new QuantityParser(intergalacticToRoman);
        quantityParser.setCalculator(new RomanNumeralCalculator());
        quantityParser.setValidator(new RomanNumeralValidator());
        answerMaterialWorth = new AnswerMaterialWorth(materialsByName, quantityParser);
    }

    @Test
    public void
    answerCalculatedCorrectly() {
        intergalacticToRoman.put("glob", I);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        String answer = answerMaterialWorth.calculateWorth("how many Credits is glob Silver ?");

        assertThat(answer, is("glob Silver is 10.0 Credits"));
    }

    @Test
    public void translationException_WhenIntergalacticQuantitiesInInvalidOrder() {
        intergalacticToRoman.put("glob", I);
        intergalacticToRoman.put("prok", V);
        intergalacticToRoman.put("pish", X);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        String answer = answerMaterialWorth.calculateWorth("how many Credits is glob prok pish Silver ?");

        assertThat(answer, is("I have no idea what you are talking about"));
    }
}
