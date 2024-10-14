package client;

import commons.Flatmate;

public record Debt(Flatmate debtor, int amount) {
    @Override
    public String toString() {
        String term = " owes ";
        int sum = amount;
        if(amount < 0) {
            term = " is owed ";
            sum = -sum;
        }
        return debtor().name() + term
                + sum/100 + "." + sum/10%10 + sum%10 + "€";
    }
}
