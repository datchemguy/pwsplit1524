package client;

import commons.Expense;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ServerClient {
    private final String host;
    private final RestTemplate template;

    ServerClient(String host, RestTemplate template) {
        this.host = host;
        this.template = template;
    }

    public ServerClient(String host) {
        this(host, new RestTemplate(List.of(new MappingJackson2HttpMessageConverter())));
    }

    public ServerClient(String host, int port) {
        this(host + ":" + port + "/");
    }

    public Expense[] getAll() {
        return template.getForEntity(host, Expense[].class).getBody();
    }

    public Expense post(Expense expense) {
        return template.postForObject(host, expense, Expense.class);
    }

    public void delete(Expense expense) {
        template.delete(host + expense.getId());
    }
}
