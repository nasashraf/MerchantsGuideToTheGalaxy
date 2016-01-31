package com.translator.domain.model.calculator;

import com.translator.domain.model.numeral.Cost;

import java.util.List;

public class CostCalculator {

    public Credits calculate(List<? extends Cost> elements) {

        return new Credits(calcCost(elements.get(0), elements.get(0).operation(0.0), elements.subList(1, elements.size())));
    }

    private Double calcCost(Cost current, Double result, List<? extends Cost> costs) {
        if (costs.isEmpty()) return result;

        if (costs.size() == 1) {
            Cost more = current.next(costs.get(0));
            return more.operation(result);
        }

        Cost next = current.next(costs.get(0));
        Double newResult = next.operation(result);

        return calcCost(next, newResult, costs.subList(1, costs.size()));
    }
}
