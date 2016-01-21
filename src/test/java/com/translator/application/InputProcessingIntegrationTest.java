package com.translator.application;

import com.translator.application.test.doubles.ConsoleSpy;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class InputProcessingIntegrationTest {

    private ConsoleSpy consoleSpy;

    @Before
    public void createSUT() {
        consoleSpy = new ConsoleSpy();
    }

    @Test
    public void
    correctAnswerWrittenToConsole() {
        List<String> input = new ArrayList<String>();
        input.add("glob is I");
        input.add("glob glob Silver is 34 Credits");
        input.add("how many Credits is glob Silver ?");

        InputAdapterWithConsoleSpy inputAdapter = new InputAdapterWithConsoleSpy();

        inputAdapter.adaptAndProcess(input);

        assertThat(consoleSpy.outputsWritten, contains("glob Silver is 17.0 Credits"));
    }

    private List<String> createList(final String element) {
        return new ArrayList<String>() {{
            add(element);
        }};
    }

    private class InputAdapterWithConsoleSpy extends InputProcessingService {

        @Override
        protected IntergalacticTranslationProcessor createIntergalacticTranslationProcessor(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName) {
            IntergalacticTranslationProcessor intergalacticTranslationProcessor = new IntergalacticTranslationProcessor(intergalacticToRoman, materialsByName);
            intergalacticTranslationProcessor.setConsole(consoleSpy);
            return intergalacticTranslationProcessor;
        }
    }
}
