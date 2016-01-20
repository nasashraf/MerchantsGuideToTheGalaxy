package com.translator.application;

import com.translator.domain.model.calculator.Calculator;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.domain.model.validation.Validator;
import com.translator.infrastructure.Console;

import java.util.List;
import java.util.Map;

public class IntergalacticTranslationProcessor {

    private Map<String, RomanNumeral> intergalacticToRoman;
    private Map<String, Material> materialsByName;
    private Console console;
    private Calculator creditsCalculator;
    private Validator validator;


    public IntergalacticTranslationProcessor(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material > materialsByName) {
        this.intergalacticToRoman = intergalacticToRoman;
        this.materialsByName = materialsByName;
    }

    public void process(List<String> questions) {
        IntergalacticTranslator intergalacticTranslator = new IntergalacticTranslator(intergalacticToRoman, materialsByName);
        intergalacticTranslator.setCalculator(creditsCalculator);
        intergalacticTranslator.setValidator(validator);

        if (questions.isEmpty()) {
            return;
        }

        for (String question : questions) {
            String answer = intergalacticTranslator.translate(question);
            console.write(answer);
        }
    }

    protected void setConsole(Console console) {
        this.console = console;
    }

    protected void setCalculator(Calculator calculator) {
        this.creditsCalculator = calculator;
    }

    protected void setValidator(Validator validator) {
        this.validator = validator;
    }
}
