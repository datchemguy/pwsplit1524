package commons;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) private long id;
    @Enumerated(EnumType.STRING) private Flatmate payer;
    private int cost;
    private String item;

    public Expense() {this(0L, null, 0, "");}

    public Expense(long id, Flatmate payer, int cost, String item) {
        this.id = id;
        this.payer = payer;
        this.cost = cost;
        this.item = item;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Flatmate getPayer() {
        return payer;
    }

    public void setPayer(Flatmate payer) {
        this.payer = payer;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;
        return id == expense.id && cost == expense.cost && payer == expense.payer
                && Objects.equals(item, expense.item);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + Objects.hashCode(payer);
        result = 31 * result + cost;
        return result;
    }

    @Override
    public String toString() {
        return payer.name() + " paid "
                + cost/100 + "." + cost/10%10 + cost%10
                + "€ for " + item;
    }
}