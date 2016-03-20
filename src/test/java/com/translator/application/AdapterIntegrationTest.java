package com.translator.application;

import com.translator.application.test.doubles.ConsoleSpy;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.translator.domain.model.credits.Credits.credits;
import static com.translator.domain.model.material.Material.aMaterial;
import static com.translator.domain.model.numeral.RomanNumeral.I;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.IsEqual.equalTo;

public class AdapterIntegrationTest {

    private static final String INVALID_ROMAN_NUMERAL = "Z";
    private  Map<String, RomanNumeral> intergalacticToRomanGenerated;
    private Map<String, Material> materialsByNameGenerated;
    private List<String> questionsGenerated;
    private ConsoleSpy consoleSpy;


    @Before
    public void createSUT() {
        consoleSpy = new ConsoleSpy();
    }

    @Test public void
    inputAdaptedToDomainModel() {
        List<String> input = new ArrayList<String>();
        input.add("glob is I");
        input.add("glob glob Silver is 34 Credits");
        input.add("how many Credits is glob Silver ?");

        InputProcessingService inputAdapter = new InputAdapterWithStub();
        inputAdapter.setConsole(consoleSpy);

        inputAdapter.adaptAndProcess(input);

        Map<String, RomanNumeral> expectedIntergalacticToRoman = MapBuilder.<String, RomanNumeral>aMapBuilder().put("glob", I).build();
        Map<String, Material> expectedMaterialByName = MapBuilder.<String, Material>aMapBuilder().put("Silver", aMaterial("Silver", credits(17.0))).build();
        List<String> expectedQuestions = createList("how many Credits is glob Silver ?");

        assertThat(questionsGenerated, equalTo(expectedQuestions));
        assertThat(intergalacticToRomanGenerated, equalTo(expectedIntergalacticToRoman));
        assertThat(materialsByNameGenerated, equalTo(expectedMaterialByName));
    }


    @Test public void
    adaptationFails_WhenIntergalacticPhraseDoesNotMatchARomanNumeral() {
        List<String> input = new ArrayList<String>();
        input.add("glob is " + INVALID_ROMAN_NUMERAL);
        input.add("glob glob Silver is 34 Credits");
        input.add("how many Credits is glob Silver ?");

        InputProcessingService inputAdapter = new InputAdapterWithStub();
        inputAdapter.setConsole(consoleSpy);

        inputAdapter.adaptAndProcess(input);

        assertThat(consoleSpy.outputsWritten, contains("ERROR - failed to convert intergalactic phrase to a valid roman numeral"));
    }

    private List<String> createList(final String element) {
        return new ArrayList<String>() {{
            add(element);
        }};
    }

    private class InputAdapterWithStub extends InputProcessingService {

        @Override
        protected IntergalacticWorthCalculationProcessor intergalacticTranslationProcessor(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName) {
            return new IntergalacticTranslationProcessorStub(intergalacticToRoman, materialsByName);
        }
    }

    private class IntergalacticTranslationProcessorStub extends IntergalacticWorthCalculationProcessor {

        public IntergalacticTranslationProcessorStub(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName) {
            super(intergalacticToRoman, materialsByName);
            intergalacticToRomanGenerated = intergalacticToRoman;
            materialsByNameGenerated = materialsByName;
        }

        @Override
        public void process(List<String> questions) {
            questionsGenerated = questions;
        }

    }
}
