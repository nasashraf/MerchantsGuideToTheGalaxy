package com.translator.domain.model.calculator;

import com.translator.domain.model.numeral.Cost;

import java.util.List;

public class CostCalculator {

    public Credits calculate(List<? extends Cost> elements) {

        return new Credits(calcCost(head(elements), head(elements).operation(0.0), tail(elements)));
    }

    private Double calcCost(Cost current, Double result, List<? extends Cost> costs) {
        if (costs.isEmpty()) return result;

        if (costs.size() == 1) {
            Cost more = current.next(head(costs));
            return more.operation(result);
        }

        Cost next = current.next(head(costs));
        Double newResult = next.operation(result);

        return calcCost(next, newResult, tail(costs));
    }

    private Cost head(List<? extends Cost> elements) {
        return elements.get(0);
    }

    private List<? extends Cost> tail(List<? extends Cost> elements) {
        return elements.subList(1, elements.size());
    }
}
