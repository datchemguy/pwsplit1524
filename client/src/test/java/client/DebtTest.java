package client;

import commons.Flatmate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebtTest {
    @Test
    public void toStringTest() {
        assertEquals("Thaejus owes 64.23€", new Debt(Flatmate.Thaejus, 6423).toString());
        assertEquals("Bruno is owed 5.70€", new Debt(Flatmate.Bruno, -570).toString());
    }
}
