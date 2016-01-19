package com.translator.domain.model.calculator;

public class Credits {
    private Double amount;

    public Credits(Double amount) {
        this.amount = amount;
    }

    public static Credits credits(Double amount) {
        return new Credits(amount);
    }

    public String amount() {
        return amount.toString();
    }

    public Credits multipliedBy(Integer amount) {
        return new Credits(this.amount * amount);
    }

    public Credits dividedBy(Integer decimalAmount) {
        return new Credits(amount / decimalAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credits)) return false;

        Credits credits = (Credits) o;

        if (!(amount instanceof Double)) return false;
        if (!(credits.amount instanceof Double)) return false;

        boolean result = amount.equals(credits.amount);
        return result;

    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

    @Override
    public String toString() {
        return "Credits(" + amount + ")";
    }
}
