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

    public Credits multipliedBy(Credits another) {
        return new Credits(this.amount * another.amount);
    }

    public Credits dividedBy(Credits another) {
        return new Credits(amount / another.amount);
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

    public Credits plus(Credits another) {
        return new Credits(this.amount + another.amount);
    }

    public Credits minus(Credits another) {
        return new Credits(this.amount - another.amount);
    }

    public Credits multipliedByTwo() {
        return new Credits(amount * 2);
    }

    public boolean greaterThanOrEqualTo(Credits value) {
        return this.amount >= value.amount;
    }

    public boolean lessThan(Credits another) {
        return this.amount < another.amount;
    }
}
