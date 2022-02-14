package com.example.homebudget.repository;

import com.example.homebudget.models.Budget;
import org.springframework.data.repository.CrudRepository;

public interface BudgetRepository extends CrudRepository<Budget, Long> {

}


