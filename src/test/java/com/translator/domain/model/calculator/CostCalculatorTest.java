package com.translator.domain.model.calculator;

import com.translator.domain.model.numeral.Cost;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.List;

import static com.translator.domain.model.calculator.Credits.credits;
import static com.translator.domain.model.numeral.RomanNumeralNew.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CostCalculatorTest {

    @Test public void
    worthWithoutMaterialIsValueOfAmount_ForDifferentAmountsExpressedInRomanNUmerals() {
//        assertThat(costOf(), isWorth(credits(0.0)));
        assertThat(costOf(I), isWorth(credits(1.0)));

        assertThat(costOf(I,I), isWorth(credits(2.0)));
        assertThat(costOf(I,I,I), isWorth(credits(3.0)));

        assertThat(costOf(V), isWorth(credits(5.0)));
        assertThat(costOf(V,I), isWorth(credits(6.0)));
        assertThat(costOf(V,I,I), isWorth(credits(7.0)));
        assertThat(costOf(V,I,I,I), isWorth(credits(8.0)));

        assertThat(costOf(X), isWorth(credits(10.0)));
        assertThat(costOf(L), isWorth(credits(50.0)));
        assertThat(costOf(C), isWorth(credits(100.0)));
        assertThat(costOf(D), isWorth(credits(500.0)));
        assertThat(costOf(M), isWorth(credits(1000.0)));
        assertThat(costOf(M,D,C,L,X,V,I), isWorth(credits(1666.0)));

        assertThat(costOf(I,V), isWorth(credits(4.0)));
        assertThat(costOf(X,I,V), is(credits(14.0)));

        assertThat(costOf(I,X), isWorth(credits(9.0)));
        assertThat(costOf(X,I,X), isWorth(credits(19.0)));

        assertThat(costOf(X,L), isWorth(credits(40.0)));
        assertThat(costOf(X,L,V), isWorth(credits(45.0)));

        assertThat(costOf(X,C), isWorth(credits(90.0)));
        assertThat(costOf(C,X,C), isWorth(credits(190.0)));

        assertThat(costOf(C,D), isWorth(credits(400.0)));
        assertThat(costOf(M,C,D), isWorth(credits(1400.0)));

        assertThat(costOf(C,M), isWorth(credits(900.0)));
        assertThat(costOf(M,C,M), isWorth(credits(1900.0)));

        assertThat(costOf(C,D,L,X,X,X,I,X), isWorth(credits(489.0)));
        assertThat(costOf(C,M,X,C,I,X), isWorth(credits(999.0)));
        assertThat(costOf(M,M,M,M,C,M,X,C,I,X), isWorth(credits(4999.0)));
    }

    private Credits costOf(Cost... costs) {
        List<? extends Cost> costsList = asList(costs);

        CostCalculator costCalculator = new CostCalculator();

        Credits calculate = costCalculator.calculate(costsList);
        return calculate;
    }

    private Matcher<Credits> isWorth(Credits credits) {
        return is(credits);
    }



}
