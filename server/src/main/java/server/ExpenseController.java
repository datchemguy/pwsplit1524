package server;

import commons.Expense;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/")
public class ExpenseController {
    private final ExpenseRepository repo;

    public ExpenseController(ExpenseRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<Expense>> findAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Expense> findById(@PathVariable long id) {
        if(!repo.existsById(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<Expense> save(@RequestBody Expense expense) {
        Expense res = repo.save(expense);
        return ResponseEntity.created(URI.create("/" + res.getId())).body(res);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        repo.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        if(!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
