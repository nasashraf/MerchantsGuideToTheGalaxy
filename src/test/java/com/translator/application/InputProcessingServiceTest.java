package com.translator.application;

import com.translator.application.test.doubles.*;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.translator.application.InputCategoriser.*;
import static com.translator.domain.model.credits.Credits.credits;
import static com.translator.domain.model.material.Material.aMaterial;
import static com.translator.domain.model.numeral.RomanNumeral.I;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.IsEqual.equalTo;

public class InputProcessingServiceTest {

    public static final String INVALID_ROMAN_NUMERAL = "Z";
    private InputCategoriserSpy inputCategoriserSpy;
    private IntergalacticToRomanAdapterSpy intergalacticToRomanAdapterSpy;
    private MaterialsAdapterSpy materialsAdapterSpy;
    private IntergalacticWorthCalculationProcessorSpy intergalacticTranslationProcessorSpy;
    private ConsoleSpy consoleSpy;
    private InputProcessingService inputAdapter;

    @Before
    public void createSUT() {
        inputCategoriserSpy = new InputCategoriserSpy();
        materialsAdapterSpy = new MaterialsAdapterSpy(null);
        intergalacticToRomanAdapterSpy = new IntergalacticToRomanAdapterSpy();
        intergalacticTranslationProcessorSpy = new IntergalacticWorthCalculationProcessorSpy(null, null);
        consoleSpy = new ConsoleSpy();

        inputAdapter = new InputAdapterWithStub();
        inputAdapter.setConsole(consoleSpy);
    }

    @Test public void
    inputProcessed() {
        List<String> input = new ArrayList<String>();
        input.add("glob is I");
        input.add("glob glob Silver is 34 Credits");
        input.add("how many Credits is glob Silver ?");

        List<String> conversions = createList("glob is I");
        List<String> materials = createList("glob glob Silver is 34 Credits");
        List<String> questions = createList("how many Credits is glob Silver ?");

        Map<String, List<String>> categories = new HashMap<String, List<String>>();
        categories.put(CONVERSIONS, conversions);
        categories.put(MATERIAL_COSTS, materials);
        categories.put(QUESTIONS, questions);

        Map<String, RomanNumeral> intergalacticToRoman = MapBuilder.<String, RomanNumeral>aMapBuilder().put("glob", I).build();
        Map<String, Material> materialByName = MapBuilder.<String, Material>aMapBuilder().put("Silver", aMaterial("Silver", credits(17.0))).build();

        inputCategoriserSpy.setCategoriesStub(categories);
        intergalacticToRomanAdapterSpy.setIntergalacticToRomanStub(intergalacticToRoman);
        materialsAdapterSpy.setMaterialByStub(materialByName);

        inputAdapter.adaptAndProcess(input);

        assertThat(inputCategoriserSpy.categoriseCalledWith, equalTo(input));
        assertThat(intergalacticToRomanAdapterSpy.convertCalledWith, equalTo(conversions));
        assertThat(materialsAdapterSpy.intergalacticToRomanCreatedWith, equalTo(intergalacticToRoman));
        assertThat(materialsAdapterSpy.createMaterialCalledWith, equalTo(materials));
        assertThat(intergalacticTranslationProcessorSpy.intergalacticToRomanCreatedWith, equalTo(intergalacticToRoman));
        assertThat(intergalacticTranslationProcessorSpy.materialsByNameCreatedWith, equalTo(materialByName));
        assertThat(intergalacticTranslationProcessorSpy.questionsCalledWith, equalTo(questions));
    }


    @Test public void
    errorMessagereported_WhenFailedToConvertIntergalacticPhraseToValidRomanNumeral() {
        List<String> input = new ArrayList<String>();
        input.add("glob is " + INVALID_ROMAN_NUMERAL);
        input.add("glob glob Silver is 34 Credits");
        input.add("how many Credits is glob Silver ?");

        List<String> conversions = createList("glob is I");
        List<String> materials = createList("glob glob Silver is 34 Credits");
        List<String> questions = createList("how many Credits is glob Silver ?");

        Map<String, List<String>> categories = new HashMap<String, List<String>>();
        categories.put(CONVERSIONS, conversions);
        categories.put(MATERIAL_COSTS, materials);
        categories.put(QUESTIONS, questions);

        inputCategoriserSpy.setCategoriesStub(categories);

        InputAdapterStubWithRomanNumerAdapterExceptions inputAdapterStubWithExceptions = new InputAdapterStubWithRomanNumerAdapterExceptions();
        inputAdapterStubWithExceptions.setConsole(consoleSpy);

        inputAdapterStubWithExceptions.adaptAndProcess(input);

        assertThat(consoleSpy.outputsWritten, contains("ERROR - failed to convert intergalactic phrase to a valid roman numeral"));
    }


    @Test public void
    errorMessagereported_WhenFailedToDertmineMaterialCost() {
        List<String> input = new ArrayList<String>();
        input.add("glob is I");
        input.add("glob glob Silver is 34 Credits");
        input.add("how many Credits is glob Silver ?");

        List<String> conversions = createList("glob is I");
        List<String> materials = createList("glob glob Silver is 34 Credits");
        List<String> questions = createList("how many Credits is glob Silver ?");

        Map<String, List<String>> categories = new HashMap<String, List<String>>();
        categories.put(CONVERSIONS, conversions);
        categories.put(MATERIAL_COSTS, materials);
        categories.put(QUESTIONS, questions);

        Map<String, RomanNumeral> intergalacticToRoman = MapBuilder.<String, RomanNumeral>aMapBuilder().put("glob", I).build();

        inputCategoriserSpy.setCategoriesStub(categories);
        intergalacticToRomanAdapterSpy.setIntergalacticToRomanStub(intergalacticToRoman);

        InputAdapterStubWithMaterialsAdapterExceptions inputAdapterStubWithExceptions = new InputAdapterStubWithMaterialsAdapterExceptions();
        inputAdapterStubWithExceptions.setConsole(consoleSpy);

        inputAdapterStubWithExceptions.adaptAndProcess(input);

        assertThat(consoleSpy.outputsWritten, contains("ERROR - failed to determine the cost of materials"));
    }


    private List<String> createList(final String element) {
        return new ArrayList<String>() {{
            add(element);
        }};
    }

    private class InputAdapterWithStub extends InputProcessingService {

        @Override
        protected InputCategoriser createInputCategoriser() {
            return inputCategoriserSpy;
        }

        @Override
        protected IntergalacticToRomanAdapter createIntergalacticToRomanAdapter() {

            return intergalacticToRomanAdapterSpy;
        }

        @Override
        protected MaterialsAdapter createMaterialsAdapter(Map<String, RomanNumeral> intergalacticToRoman) {
            materialsAdapterSpy.intergalacticToRomanCreatedWith = intergalacticToRoman;
            return materialsAdapterSpy;
        }
        @Override
        protected IntergalacticWorthCalculationProcessor intergalacticTranslationProcessor(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName) {
            intergalacticTranslationProcessorSpy.intergalacticToRomanCreatedWith = intergalacticToRoman;
            intergalacticTranslationProcessorSpy.materialsByNameCreatedWith = materialsByName;
            return intergalacticTranslationProcessorSpy;
        }

    }

    private class InputAdapterStubWithRomanNumerAdapterExceptions extends InputProcessingService {

        @Override
        protected InputCategoriser createInputCategoriser() {
            return inputCategoriserSpy;
        }

        @Override
        protected IntergalacticToRomanAdapter createIntergalacticToRomanAdapter() {
            return new IntergalacticToRomanAdapterExceptionStub();
        }

        @Override
        protected MaterialsAdapter createMaterialsAdapter(Map<String, RomanNumeral> intergalacticToRoman) {
            materialsAdapterSpy.intergalacticToRomanCreatedWith = intergalacticToRoman;
            return materialsAdapterSpy;
        }

        @Override
        protected IntergalacticWorthCalculationProcessor intergalacticTranslationProcessor(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName) {
            intergalacticTranslationProcessorSpy.intergalacticToRomanCreatedWith = intergalacticToRoman;
            intergalacticTranslationProcessorSpy.materialsByNameCreatedWith = materialsByName;
            return intergalacticTranslationProcessorSpy;
        }

    }


    private class InputAdapterStubWithMaterialsAdapterExceptions extends InputProcessingService {

        @Override
        protected InputCategoriser createInputCategoriser() {
            return inputCategoriserSpy;
        }

        @Override
        protected IntergalacticToRomanAdapter createIntergalacticToRomanAdapter() {
            return intergalacticToRomanAdapterSpy;
        }

        @Override
        protected MaterialsAdapter createMaterialsAdapter(Map<String, RomanNumeral> intergalacticToRoman) {
            return new MaterialsAdapterExceptionStub(intergalacticToRoman);
        }

        @Override
        protected IntergalacticWorthCalculationProcessor intergalacticTranslationProcessor(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName) {
            intergalacticTranslationProcessorSpy.intergalacticToRomanCreatedWith = intergalacticToRoman;
            intergalacticTranslationProcessorSpy.materialsByNameCreatedWith = materialsByName;
            return intergalacticTranslationProcessorSpy;
        }

    }

}
