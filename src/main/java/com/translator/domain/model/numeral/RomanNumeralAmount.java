package com.translator.domain.model.numeral;

import java.util.*;

import static com.translator.domain.model.numeral.RomanNumeral.*;
import static java.util.Arrays.asList;

public class RomanNumeralAmount {

    private static final int AT_FIRST_POSITION = 0;
    private static final int AT_SECOND_POSITION = 1;

    private static final HashMap<List<RomanNumeral>, Integer> SUBTRACTION_NUMERAL_VALUES = new HashMap<List<RomanNumeral>, Integer>(){{
        put(asList(I,V), 4);
        put(asList(I,X), 9);
        put(asList(X,L), 40);
        put(asList(X,C), 90);
        put(asList(C,D), 400);
        put(asList(C,M), 900);
    }};

    private List<RomanNumeral> numerals;

    public RomanNumeralAmount(List<RomanNumeral> numerals) {
        this.numerals = numerals;
    }

    public static RomanNumeralAmount aRomanNumeralAmount(RomanNumeral... numerals) {
        return new RomanNumeralAmount(asList(numerals));
    }

    public static RomanNumeralAmount aRomanNumeralAmount(List<RomanNumeral> numerals) {
        return new RomanNumeralAmount(numerals);
    }

    public Integer decimalValue() {
        return calculateDecimalValue(0, numerals);
    }

    private Integer calculateDecimalValue(Integer total, List<RomanNumeral> romanNumerals) {
        if (romanNumerals.isEmpty()) return total;
        if (romanNumerals.size() == 1) return total + romanNumerals.get(0).decimalValue();

        RomanNumeral firstSymbol = getSymbolFom(romanNumerals, AT_FIRST_POSITION);
        RomanNumeral secondSymbol = getSymbolFom(romanNumerals, AT_SECOND_POSITION);

        List<RomanNumeral> firstPairOfNumerals = asList(firstSymbol,secondSymbol);

        Integer index=1;
        Integer currentTotal = total;
        if (SUBTRACTION_NUMERAL_VALUES.containsKey(firstPairOfNumerals)) {
            index=2;
            currentTotal += SUBTRACTION_NUMERAL_VALUES.get(firstPairOfNumerals);
        } else {
            currentTotal += firstSymbol.decimalValue();
        }

        return calculateDecimalValue(currentTotal, tailFrom(index, romanNumerals));
    }

    private RomanNumeral getSymbolFom(List<RomanNumeral> romanNumeralNumber, Integer index) {
        return romanNumeralNumber.get(index);
    }

    private List<RomanNumeral> tailFrom(Integer index, List<RomanNumeral> romanNumeralNumber) {
        return romanNumeralNumber.subList(index, romanNumeralNumber.size());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RomanNumeralAmount)) return false;

        RomanNumeralAmount that = (RomanNumeralAmount) o;

        return numerals.equals(that.numerals);

    }

    @Override
    public int hashCode() {
        return numerals.hashCode();
    }
}
