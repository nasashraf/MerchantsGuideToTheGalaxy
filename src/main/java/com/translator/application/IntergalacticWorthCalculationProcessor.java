package com.translator.application;

import com.translator.domain.model.calculator.Calculator;
import com.translator.domain.model.calculator.CostCalculator;
import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.domain.model.validation.RomanNumeralValidator;
import com.translator.domain.model.validation.Validator;
import com.translator.infrastructure.Screen;

import java.util.List;
import java.util.Map;

public class IntergalacticWorthCalculationProcessor {

    private Map<String, RomanNumeral> intergalacticToRoman;
    private Map<String, Material> materialsByName;
    private Console console;
    private Calculator creditsCalculator;
    private Validator validator;


    public IntergalacticWorthCalculationProcessor(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName) {
        this.intergalacticToRoman = intergalacticToRoman;
        this.materialsByName = materialsByName;

        creditsCalculator = new CostCalculator();
        validator = new RomanNumeralValidator();
        console = new Screen();
    }

    public void process(List<String> questions) {
        for (String question : questions) {
            AnsweringFactory factory = new AnsweringFactory(creditsCalculator, validator);

            AnsweringService answeringService = factory.create(question, intergalacticToRoman, materialsByName);

            String answer = answeringService.calculateWorth(question);

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
