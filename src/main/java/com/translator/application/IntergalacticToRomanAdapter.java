package com.translator.application;

import com.translator.domain.model.numeral.RomanNumeral;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.translator.domain.model.numeral.RomanNumeral.*;

public class IntergalacticToRomanAdapter {
    private static final String TRANSLATION_DIVIDING_KEYPHRASE = " is ";

    private static final HashMap<String, RomanNumeral> TEXT_ROMAN_TO_NUMERAL = new HashMap<String, RomanNumeral>(){{
        put("I", I);
        put("V", V);
        put("X", X);
        put("L", L);
        put("C", C);
        put("D", D);
        put("M", M);
    }};


    public Map<String, RomanNumeral> convert(List<String> translations) {
        Map<String, RomanNumeral> intergalacticToRoman = new HashMap<String, RomanNumeral>();

        if (translations.isEmpty()) {
            return intergalacticToRoman;
        }

        for (String translation : translations) {
            String[] text = translation.split(TRANSLATION_DIVIDING_KEYPHRASE);

            if (isTranslationInvalid(text)) {
               throw new TranslationException();
            }

            intergalacticToRoman.put(intergalacticFrom(text), romanNumeralFrom(text));
        }

        return intergalacticToRoman;
    }


    public class TranslationException extends RuntimeException { }

    private boolean isTranslationInvalid(String[] text) {
        if (translationPhraseIsBad(text)) {
            return true;
        }

        if (noRomanNumeralFound(text)){
            return true;
        }

        return false;
    }

    private boolean translationPhraseIsBad(String[] text) {
        return text.length != 2;
    }

    private boolean noRomanNumeralFound(String[] text) {
        return romanNumeralFrom(text) == null;
    }

    private String intergalacticFrom(String[] text) {
        return text[0].trim();
    }

    private RomanNumeral romanNumeralFrom(String[] text) {
        return TEXT_ROMAN_TO_NUMERAL.get(text[1].trim());
    }
}
