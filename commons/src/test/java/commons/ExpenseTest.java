package commons;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {
    @Test
    public void constructorTest() {
        Expense e = new Expense(4, Flatmate.Swarup, 574, "no");
        assertEquals(4, e.getId());
        assertEquals(Flatmate.Swarup, e.getPayer());
        assertEquals(574, e.getCost());
        assertEquals("no", e.getItem());
    }

    @Test
    public void reducedConstructorTest() {
        Expense e = new Expense(Flatmate.Bruno, 3, "ggg");
        assertEquals(Flatmate.Bruno, e.getPayer());
        assertEquals(3, e.getCost());
        assertEquals("ggg", e.getItem());
    }

    @Test
    public void emptyConstructorTest() {
        Expense e = new Expense();
        assertNull(e.getPayer());
    }

    @ParameterizedTest
    @MethodSource("setters")
    public <T> void setTest(T value, BiConsumer<Expense, T> setter, Function<Expense, T> getter) {
        Expense e = new Expense(8, Flatmate.Bruno, 3, "ggg");
        setter.accept(e, value);
        assertEquals(value, getter.apply(e));
    }

    private static Stream<Arguments> setters() {
        return Stream.of(
                Arguments.of(4L, (BiConsumer<Expense, Long>) Expense::setId, (Function<Expense, Long>) Expense::getId),
                Arguments.of(Flatmate.Tobiasz, (BiConsumer<Expense, Flatmate>) Expense::setPayer, (Function<Expense, Flatmate>) Expense::getPayer),
                Arguments.of(25, (BiConsumer<Expense, Integer>) Expense::setCost, (Function<Expense, Integer>) Expense::getCost),
                Arguments.of("why", (BiConsumer<Expense, String>) Expense::setItem, (Function<Expense, String>) Expense::getItem)
        );
    }

    @Test
    public void equalsTest() {
        Expense e1 = new Expense(7, Flatmate.Himanshu, 19, "g"),
                e2 = new Expense(7, Flatmate.Himanshu, 19, "g"),
                e3 = new Expense(2, Flatmate.Bruno, 4, "h");
        assertTrue(e1.equals(e1));
        assertFalse(e1.equals(null));
        assertFalse(e1.equals("yes"));
        assertFalse(e1.equals(e3));
        assertTrue(e1.equals(e2));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(new Expense(5, Flatmate.Alessandra, 7, "s").hashCode(),
                new Expense(5, Flatmate.Alessandra, 7, "s").hashCode());
    }

    @Test
    public void toStringTest() {
        assertEquals("David paid 3.72€ for hejs",
                new Expense(Flatmate.David, 372, "hejs").toString());
    }
}
