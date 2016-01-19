package com.translator.application;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.translator.application.InputCategoriser.CONVERSIONS;
import static com.translator.application.InputCategoriser.MATERIAL_COSTS;
import static com.translator.application.InputCategoriser.QUESTIONS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

public class InputCategoriserTest {

    private InputCategoriser categoriser;

    @Before
    public void createSUT() {
        categoriser = new InputCategoriser();
    }

    @Test
    public void questionCategorised_WhenInputOnlyContainsAQuestion() {
        List<String> input = new ArrayList<String>() {{
          add("how many Credits is glob prok Gold ?");
        }};

        Map<String, List<String>> inputCatergories = categoriser.categorise(input);

        List<String> questions = inputCatergories.get(QUESTIONS);
        assertThat(questions.size(), is(1));
        assertThat(questions, containsInAnyOrder("how many Credits is glob prok Gold ?"));
    }

    @Test
    public void questionCategorised_WhenInputContainsMultipleQuestions() {
        List<String> input = new ArrayList<String>() {{
            add("how many Credits is glob prok Gold ?");
            add("how many Credits is glob prok Iron ?");
        }};

        Map<String, List<String>> inputCatergories = categoriser.categorise(input);

        List<String> questions = inputCatergories.get(QUESTIONS);
        assertThat(questions.size(), is(2));
        assertThat(questions, containsInAnyOrder("how many Credits is glob prok Gold ?"
                                                , "how many Credits is glob prok Iron ?"));
    }

    @Test
    public void maetrialCostCategorised_WhenInputOnlyContainsSingleMaterial() {
        List<String> input = new ArrayList<String>() {{
            add("glob glob Silver is 34 Credits");
        }};

        Map<String, List<String>> inputCatergories = categoriser.categorise(input);
        List<String> materialCosts = inputCatergories.get(MATERIAL_COSTS);

        assertThat(materialCosts.size(), is(1));
        assertThat(materialCosts, containsInAnyOrder("glob glob Silver is 34 Credits"));
    }


    @Test
    public void maetrialCostCategorised_WhenInputContainsMultipleMaterial() {
        List<String> input = new ArrayList<String>() {{
            add("glob glob Silver is 34 Credits");
            add("glob prok Gold is 57800 Credits");
        }};

        Map<String, List<String>> inputCatergories = categoriser.categorise(input);
        List<String> materialCosts = inputCatergories.get(MATERIAL_COSTS);

        assertThat(materialCosts.size(), is(2));
        assertThat(materialCosts, containsInAnyOrder("glob glob Silver is 34 Credits"
                                                   , "glob prok Gold is 57800 Credits"));
    }

    @Test
    public void conversionCategorised_WhenInputOnlyContainsSingleConversionText() {
        List<String> input = new ArrayList<String>() {{
            add("glob is I");
        }};

        Map<String, List<String>> inputCatergories = categoriser.categorise(input);
        List<String> conversions = inputCatergories.get(CONVERSIONS);

        assertThat(conversions.size(), is(1));
        assertThat(conversions, containsInAnyOrder("glob is I"));
    }

    @Test
    public void conversionCategorised_WhenInputContainsMultipleConversionText() {
        List<String> input = new ArrayList<String>() {{
            add("glob is I");
            add("prok is V");
        }};

        Map<String, List<String>> inputCatergories = categoriser.categorise(input);
        List<String> conversions = inputCatergories.get(CONVERSIONS);

        assertThat(conversions.size(), is(2));
        assertThat(conversions, containsInAnyOrder("glob is I", "prok is V"));
    }

    @Test
    public void allCategoriesAppear_WhenAllCategoriesPresent() {
        List<String> input = new ArrayList<String>() {{
            add("glob is I");
            add("prok is V");
            add("pish is X");
            add("tegj is L");
            add("glob glob Silver is 34 Credits");
            add("glob prok Gold is 57800 Credits");
            add("pish pish Iron is 3910 Credits");
            add("how much is pish tegj glob glob (XLII)?");
            add("how many Credits is glob prok Silver ?");
            add("how many Credits is glob prok Gold ?");
            add("how many Credits is glob prok Iron ?");
            add("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
        }};

        Map<String, List<String>> inputCatergories = categoriser.categorise(input);
        List<String> questions = inputCatergories.get(QUESTIONS);
        List<String> materialCosts = inputCatergories.get(MATERIAL_COSTS);
        List<String> conversions = inputCatergories.get(CONVERSIONS);

        assertThat(questions.size(), is(5));
        assertThat(questions, containsInAnyOrder("how much is pish tegj glob glob (XLII)?"
                                                , "how many Credits is glob prok Gold ?"
                                                , "how many Credits is glob prok Silver ?"
                                                , "how many Credits is glob prok Iron ?"
                                                , "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"));

        assertThat(materialCosts.size(), is(3));
        assertThat(materialCosts, containsInAnyOrder("glob glob Silver is 34 Credits"
                                                   , "glob prok Gold is 57800 Credits"
                                                   , "pish pish Iron is 3910 Credits"));

        assertThat(conversions.size(), is(4));
        assertThat(conversions, containsInAnyOrder("glob is I"
                                                 , "prok is V"
                                                 , "pish is X"
                                                 , "tegj is L"));
    }
}
