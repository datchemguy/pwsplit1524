package client;

import commons.Expense;
import commons.Flatmate;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UtilsTest {
    @Test
    public void defaultItemRandomTest() {
        assertNotNull(Utils.defaultItem());
    }

    @Test
    public void defaultItemTest() {
        assertEquals("random shit", Utils.defaultItem(new Random(72)));
    }

    @Test
    public void debtsTest() {
        final Flatmate[] all = Flatmate.values();
        Debt[] debts = Utils.calculateDebts();
        assertEquals(all.length, debts.length);
        for(Debt d : debts) assertEquals(0, d.amount());
        debts = Utils.calculateDebts(new Expense(Flatmate.Tin, 50, ""));
        assertEquals(all.length, debts.length);
        for(Debt d : debts)
            assertEquals((d.debtor() == Flatmate.Tin) ? -50 : 5, d.amount());
        debts = Utils.calculateDebts(new Expense(Flatmate.Tin, 50, ""),
                new Expense(Flatmate.Alessandra, 70, ""));
        assertEquals(all.length, debts.length);
        for(Debt d : debts)
            assertEquals(switch(d.debtor()) {
                case Tin -> -43;
                case Alessandra -> -65;
                default -> 12;
            }, d.amount());
    }
}
