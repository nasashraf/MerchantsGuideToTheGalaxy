package com.translator.application.test.doubles;

import com.translator.application.adapters.IntergalacticToRomanAdapter;
import com.translator.domain.model.numeral.RomanNumeral;

import java.util.List;
import java.util.Map;

public class IntergalacticToRomanAdapterExceptionStub extends IntergalacticToRomanAdapter {

    @Override
    public Map<String, RomanNumeral> convert(List<String> translations) {
        throw new  IntergalacticToRomanAdapter.TranslationException();
    }
}
