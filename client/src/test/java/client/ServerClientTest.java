package client;

import commons.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ServerClientTest {
    private final String host = "hosttt/";
    private RestTemplate template;
    private ServerClient client;

    @BeforeEach
    public void setUp() {
        template = mock(RestTemplate.class);
        client = new ServerClient(host, template);
    }

    @Test
    public void constructorsTest() {
        client = new ServerClient("isthisgood");
        assertEquals("isthisgood", client.getHost());
        client = new ServerClient("isthis", 567);
        assertEquals("isthis:567/", client.getHost());
    }

    private final Expense[] all = {
            new Expense(0, null, 0, "0"),
            new Expense(1, null, 1, "0"),
            new Expense(2, null, 2, "0"),
            new Expense(3, null, 3, "0")
    };

    @Test
    public void getAllTest() {
        when(template.getForObject(host, Expense[].class))
                .thenReturn(all);
        assertArrayEquals(all, client.getAll());
    }

    @Test
    public void postTest() {
        when(template.postForObject(host, all[2], Expense.class))
                .thenReturn(new Expense(7, null ,2, ""));
        assertEquals(7L, client.post(all[2]).getId());
    }

    @Test
    public void deleteTest() {
        assertTrue(client.delete(all[0]));
        verify(template).delete(anyString());
    }

    @Test
    public void shitTest() {
        when(template.getForObject(anyString(), any())).thenThrow(RestClientException.class);
        assertNull(client.getAll());
        when(template.postForObject(anyString(), any(Expense.class), any())).thenThrow(RestClientException.class);
        assertNull(client.post(all[3]));
        doThrow(RestClientException.class).when(template).delete(anyString());
        assertFalse(client.delete(all[0]));
    }
}
