package com.translator.application.test.doubles;

import com.translator.application.adapters.IntergalacticToRomanAdapter;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;
import java.util.Map;

public class IntergalacticToRomanAdapterSpy extends IntergalacticToRomanAdapter {

    public List<String> convertCalledWith;
    private Map<String, RomanNumeral> intergalacticToRomanStub;

    @Override
    public Map<String, RomanNumeral> convert(List<String> translations) {
        convertCalledWith = translations;
        return intergalacticToRomanStub;
    }

    public void setIntergalacticToRomanStub(Map<String, RomanNumeral> intergalacticToRomanStub) {
        this.intergalacticToRomanStub = intergalacticToRomanStub;
    }
}
