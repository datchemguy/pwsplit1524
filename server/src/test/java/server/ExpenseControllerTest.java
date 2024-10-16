package server;

import commons.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExpenseControllerTest {
    private final ExpenseRepository repo;
    private final ExpenseController controller;
    private final long[] ids = new long[3];

    @Autowired
    public ExpenseControllerTest(ExpenseRepository repo) {
        this.repo = repo;
        controller = new ExpenseController(repo);
    }

    @BeforeEach
    public void setUp() {
        repo.deleteAll();
        ids[0] = repo.save(new Expense(3, null, 5, "g")).getId();
        ids[1] = repo.save(new Expense(35, null, 7, "xxx")).getId();
        ids[2] = repo.save(new Expense(56, null, 546, "tyh4")).getId();
    }

    @ParameterizedTest
    @ValueSource(ints = 3)
    public void findAllTest(int size) {
        var response = controller.findAll();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(size, response.getBody().size());
    }

    @Test
    public void findTest() {
        assertTrue(controller.findById(-7362).getStatusCode().is4xxClientError());
        var response = controller.findById(ids[2]);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("tyh4", response.getBody().getItem());
    }

    @Test
    public void saveTest() {
        assertTrue(controller.save(new Expense(655, null, 6763, "ddddd")).getStatusCode().is2xxSuccessful());
        findAllTest(4);
    }

    @Test
    public void deleteAllTest() {
        assertTrue(controller.deleteAll().getStatusCode().is2xxSuccessful());
        findAllTest(0);
    }

    @Test
    public void deleteTest() {
        assertTrue(controller.deleteById(-6642).getStatusCode().is4xxClientError());
        assertTrue(controller.deleteById(ids[0]).getStatusCode().is2xxSuccessful());
        findAllTest(2);
    }
}
