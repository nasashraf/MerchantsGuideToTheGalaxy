package com.translator.domain.model.calculator;

import com.translator.domain.model.numeral.Cost;

import java.util.List;

public class CostCalculator implements Calculator {

    public Credits calculate(List<? extends Cost> elements) {
        return calcCost(head(elements), head(elements).operation(Credits.credits(0.0)), tail(elements));
    }

    private Credits calcCost(Cost current, Credits currentTotal, List<? extends Cost> costs) {
        if (costs.isEmpty()) return currentTotal;

        Cost nextElement = current.next(head(costs));

        if (lastElement(costs)) {
            return nextElement.operation(currentTotal);
        }

        Credits result = nextElement.operation(currentTotal);

        return calcCost(nextElement, result, tail(costs));
    }

    private Cost head(List<? extends Cost> elements) {
        return elements.get(0);
    }

    private List<? extends Cost> tail(List<? extends Cost> elements) {
        return elements.subList(1, elements.size());
    }

    private boolean lastElement(List<? extends Cost> costs) {
        return costs.size() == 1;
    }
}
