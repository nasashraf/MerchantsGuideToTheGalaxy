package com.translator.application;

import com.translator.application.test.doubles.InputCategoriserSpy;
import com.translator.application.test.doubles.IntergalacticToRomanAdapterSpy;
import com.translator.application.test.doubles.IntergalacticTranslationProcessorSpy;
import com.translator.application.test.doubles.MaterialsAdapterSpy;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.translator.application.InputCategoriser.*;
import static com.translator.domain.model.calculator.Credits.credits;
import static com.translator.domain.model.material.Material.aMaterial;
import static com.translator.domain.model.numeral.RomanNumeral.I;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class InputAdapterTest {

    private InputCategoriserSpy inputCategoriserSpy;
    private IntergalacticToRomanAdapterSpy intergalacticToRomanAdapterSpy;
    private MaterialsAdapterSpy materialsAdapterSpy;
    private IntergalacticTranslationProcessorSpy intergalacticTranslationProcessorSpy;
    private InputAdapter inputAdapter;

    @Before
    public void createSUT() {
        inputCategoriserSpy = new InputCategoriserSpy();
        materialsAdapterSpy = new MaterialsAdapterSpy(null);
        intergalacticToRomanAdapterSpy = new IntergalacticToRomanAdapterSpy();
        intergalacticTranslationProcessorSpy = new IntergalacticTranslationProcessorSpy(null, null);

        inputAdapter = new InputAdapterWithStub();
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
        Map<String, Material> materialByName = MapBuilder.<String, Material>aMapBuilder().put("glob", aMaterial("Silver", credits(10.0))).build();

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


    private class InputAdapterWithStub extends InputAdapter {

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
        protected IntergalacticTranslationProcessor createIntergalacticTranslationProcessor(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName) {
            intergalacticTranslationProcessorSpy.intergalacticToRomanCreatedWith = intergalacticToRoman;
            intergalacticTranslationProcessorSpy.materialsByNameCreatedWith = materialsByName;
            return intergalacticTranslationProcessorSpy;
        }
    }

    private List<String> createList(final String element) {
        return new ArrayList<String>() {{
            add(element);
        }};
    }

}
