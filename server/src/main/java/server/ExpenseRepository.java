package server;

import commons.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component("repo")
public interface ExpenseRepository extends JpaRepository<Expense, Long> {}
