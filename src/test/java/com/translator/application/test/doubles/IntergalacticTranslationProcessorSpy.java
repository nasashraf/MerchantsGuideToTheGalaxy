package com.translator.application.test.doubles;

import com.translator.application.IntergalacticTranslationProcessor;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;
import java.util.Map;

public class IntergalacticTranslationProcessorSpy extends IntergalacticTranslationProcessor {

    public List<String> questionsCalledWith;
    public Map<String, RomanNumeral> intergalacticToRomanCreatedWith;
    public Map<String, Material> materialsByNameCreatedWith;

    public IntergalacticTranslationProcessorSpy(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName) {
        super(intergalacticToRoman, materialsByName);
        intergalacticToRomanCreatedWith = intergalacticToRoman;
        materialsByNameCreatedWith = materialsByName;
    }

    @Override
    public void process(List<String> questions) {
        questionsCalledWith = questions;
    }
}
