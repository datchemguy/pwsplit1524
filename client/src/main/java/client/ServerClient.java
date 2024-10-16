package client;

import commons.Expense;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ServerClient {
    private final String host;
    private final RestTemplate template;

    ServerClient(String host, RestTemplate template) {
        this.host = host;
        this.template = template;
    }

    public ServerClient(String host) {
        this(host, new RestTemplate());
    }

    public ServerClient(String host, int port) {
        this(host + ":" + port + "/");
    }

    String getHost() {
        return host;
    }

    public Expense[] getAll() {
        try {
            return template.getForObject(host, Expense[].class);
        } catch(RestClientException _) {
            return null;
        }
    }

    public Expense post(Expense expense) {
        try {
            return template.postForObject(host, expense, Expense.class);
        } catch(RestClientException _) {
            return null;
        }
    }

    public boolean delete(Expense expense) {
        try {
            template.delete(host + expense.getId());
            return true;
        } catch(RestClientException _) {
            return false;
        }
    }
}
