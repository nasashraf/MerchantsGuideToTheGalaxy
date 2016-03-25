package com.translator.application;

import com.translator.domain.model.material.Material;
import com.translator.domain.model.numeral.RomanNumeral;
import com.translator.infrastructure.Screen;

import java.util.List;
import java.util.Map;

public class IntergalacticWorthCalculationProcessor {

    private Map<String, RomanNumeral> intergalacticToRoman;
    private Map<String, Material> materialsByName;
    private Console console;
    private AbstractRomanNumeralFactory romanNumeralFactory;


    public IntergalacticWorthCalculationProcessor(Map<String, RomanNumeral> intergalacticToRoman, Map<String, Material> materialsByName) {
        this.intergalacticToRoman = intergalacticToRoman;
        this.materialsByName = materialsByName;

        romanNumeralFactory = new ProductionRomanNumeralFactory();
        console = new Screen();
    }

    public void process(List<String> questions) {
        for (String question : questions) {
            AnsweringFactory factory = new AnsweringFactory(romanNumeralFactory);

            AnsweringService answeringService = factory.create(question, intergalacticToRoman, materialsByName);

            String answer = answeringService.calculateWorth(question);

            console.write(answer);
        }
    }

    protected void setConsole(Console console) {
        this.console = console;
    }

    protected void setRomanNumeralFactory(AbstractRomanNumeralFactory romanNumeralFactory) {
        this.romanNumeralFactory = romanNumeralFactory;
    }
}
