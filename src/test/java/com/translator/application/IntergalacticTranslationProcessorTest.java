package com.translator.application;

import com.translator.application.test.doubles.CalculatorSpy;
import com.translator.application.test.doubles.ConsoleSpy;
import com.translator.application.test.doubles.ValidatorSpy;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.translator.domain.model.calculator.Credits.credits;
import static com.translator.domain.model.material.Material.aMaterial;
import static com.translator.domain.model.numeral.RomanNumeral.I;
import static com.translator.domain.model.numeral.RomanNumeral.V;
import static com.translator.domain.model.numeral.RomanNumeral.X;
import static com.translator.domain.model.numeral.RomanNumeralAmount.aRomanNumeralAmount;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

public class IntergalacticTranslationProcessorTest {

    private ConsoleSpy consoleSpy;
    private IntergalacticTranslationProcessor intergalacticTranslationProcessor;
    private CalculatorSpy calculatorSpy;
    private ValidatorSpy validatorSpy;

    private Map<String, RomanNumeral> intergalacticToRoman;
    private Map<String, Material> materialsByName;
    private List<String> questions;

    @Before
    public void createSUT() {
        consoleSpy = new ConsoleSpy();
        calculatorSpy = new CalculatorSpy();
        validatorSpy = new ValidatorSpy();

        questions = new ArrayList<String>();
        intergalacticToRoman = new HashMap<String, RomanNumeral>();
        materialsByName = new HashMap<String, Material>();

        intergalacticTranslationProcessor = new IntergalacticTranslationProcessor(intergalacticToRoman, materialsByName);
        intergalacticTranslationProcessor.setConsole(consoleSpy);
        intergalacticTranslationProcessor.setCalculator(calculatorSpy);
        intergalacticTranslationProcessor.setValidator(validatorSpy);
    }

    @Test public void
    noAnswer_WhenNoQuestionAsked() {
        intergalacticTranslationProcessor.process(questions);

        assertThat(consoleSpy.outputsWritten.size(), is(0));
    }

    @Test public void
    answerGiven_WhenSingleQuestionIsForOneIntergalacticQuantityForMaterial() {
        intergalacticToRoman.put("glob", I);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        calculatorSpy.setCreditsAmount(credits(10.0));
        validatorSpy.setValidationResult(true);

        questions.add("how many Credits is glob Silver ?");
        intergalacticTranslationProcessor.process(questions);

        assertThat(consoleSpy.outputsWritten.size(), is(1));
        assertThat(consoleSpy.outputsWritten, contains("glob Silver is 10.0 Credits"));

        assertThat(calculatorSpy.romanNumeralAmountCalledWith, contains(aRomanNumeralAmount(I)));
        assertThat(calculatorSpy.materialCalledWith, contains(aMaterial("Silver", credits(10.0))));
        assertThat(validatorSpy.romanNumeralsCalledWithInOrder, contains(asList(I)));
    }

    @Test public void
    answerGiven_WhenMultipleQuestionAreForOneIntergalacticQuantityForMaterial() {
        intergalacticToRoman.put("glob", I);
        intergalacticToRoman.put("prok", V);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        calculatorSpy.setCreditsAmount(credits(10.0));
        validatorSpy.setValidationResult(true);

        questions.add("how many Credits is prok glob Silver ?");
        intergalacticTranslationProcessor.process(questions);

        assertThat(consoleSpy.outputsWritten.size(), is(1));
        assertThat(consoleSpy.outputsWritten, contains("prok glob Silver is 10.0 Credits"));

        assertThat(calculatorSpy.romanNumeralAmountCalledWith, contains(aRomanNumeralAmount(V,I)));
        assertThat(calculatorSpy.materialCalledWith, contains(aMaterial("Silver", credits(10.0))));
        assertThat(validatorSpy.romanNumeralsCalledWithInOrder, contains(asList(V,I)));
    }

    @Test public void
    answerGiven_WhenMultipleQuestionAreForDiffererntQuantitesOfMaterial() {
        intergalacticToRoman.put("glob", I);
        intergalacticToRoman.put("prok", V);

        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));
        materialsByName.put("Gold", aMaterial("Gold", credits(20.0)));

        calculatorSpy.setCreditsAmount(credits(150.0));
        validatorSpy.setValidationResult(true);

        questions.add("how many Credits is glob Silver ?");
        questions.add("how many Credits is glob prok Gold ?");
        intergalacticTranslationProcessor.process(questions);

        assertThat(consoleSpy.outputsWritten.size(), is(2));
        assertThat(consoleSpy.outputsWritten, contains("glob Silver is 150.0 Credits"
                                                       ,"glob prok Gold is 150.0 Credits"));

        assertThat(validatorSpy.romanNumeralsCalledWithInOrder, contains(asList(I)
                                                                        ,asList(I,V)));

        assertThat(calculatorSpy.romanNumeralAmountCalledWith, contains(aRomanNumeralAmount(I)
                                                                       ,aRomanNumeralAmount(I,V)));

        assertThat(calculatorSpy.materialCalledWith, contains(aMaterial("Silver", credits(10.0))
                                                             ,aMaterial("Gold", credits(20.0))));

    }

    @Test public void
    translationException_WhenIntergalacticQuantitiesInInvalidOrder() {
        intergalacticToRoman.put("glob", I);
        intergalacticToRoman.put("prok", V);
        intergalacticToRoman.put("pish", X);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        validatorSpy.setValidationResult(false);

        questions.add("how many Credits is glob prok pish Silver ?");
        intergalacticTranslationProcessor.process(questions);

        assertThat(consoleSpy.outputsWritten, contains("I have no idea what you are talking about"));
    }

    @Test public void
    translationException_WhenMaterialDoesNotExist() {
        intergalacticToRoman.put("glob", I);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        validatorSpy.setValidationResult(true);

        questions.add("how many Credits is glob Gold ?");
        intergalacticTranslationProcessor.process(questions);

        assertThat(consoleSpy.outputsWritten, contains("I have no idea what you are talking about"));
    }

    @Test public void
    translationException_WhenRomanNumeralForIntergalacticQuantityDoesNotExist() {
        intergalacticToRoman.put("prok", V);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        validatorSpy.setValidationResult(true);

        questions.add("how many Credits is glob Silver ?");
        intergalacticTranslationProcessor.process(questions);

        assertThat(consoleSpy.outputsWritten, contains("I have no idea what you are talking about"));
    }

    @Test public void
    translationException_WhenBadlyFormattedQuestion() {
        intergalacticToRoman.put("glob", I);
        materialsByName.put("Silver", aMaterial("Silver", credits(10.0)));

        calculatorSpy.setCreditsAmount(credits(10.0));

        validatorSpy.setValidationResult(true);

        questions.add("how many Credits is globSilver ?");
        intergalacticTranslationProcessor.process(questions);

        assertThat(consoleSpy.outputsWritten, contains("I have no idea what you are talking about"));
    }
}
