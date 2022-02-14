package com.example.homebudget.controllers;

import com.example.homebudget.entity.Category;
import com.example.homebudget.models.Budget;
import com.example.homebudget.models.CategoryWithAmount;
import com.example.homebudget.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@Controller
public class BudgetController {
    @Autowired
    private BudgetRepository budgetRepository;
    private Model model;

    @GetMapping("/budget")
    public String budget(Model model) {
       List<Budget> budgets = (List<Budget>) budgetRepository.findAll();
       Map<String, CategoryWithAmount> chartDataRaw = new HashMap<>();
       for(Budget budget: budgets){
           CategoryWithAmount categoryWithAmount = chartDataRaw.get(budget.getCategory().name());
           if(categoryWithAmount == null){
               categoryWithAmount = new CategoryWithAmount();
               categoryWithAmount.setCategory(budget.getCategory().name());
               chartDataRaw.put(budget.getCategory().name(), categoryWithAmount);
           }
           categoryWithAmount.setTotalAmount(categoryWithAmount.getTotalAmount() + budget.getAmount());
           Collection<CategoryWithAmount> values = chartDataRaw.values();
           List<List> listWithData = new ArrayList<>();
           values.forEach(x -> {
               List objectList = new ArrayList();
               objectList.add(x.getCategory());
               objectList.add(x.getTotalAmount());
               listWithData.add(objectList);
           });
           model.addAttribute("chartData", listWithData);
       }

        model.addAttribute("budgets", budgets);
        model.addAttribute("categories", Category.values());
        return "budget";
    }

    @PostMapping("/budget")
    public String budgetAdd(@RequestParam String category, @RequestParam Integer amount,  @RequestParam String description,
            Model model)
            {
        Budget budget = new Budget(category, amount, description);
        budget.setDate(LocalDate.now());
        budget.setAmount(amount);
        budget.setCategory(Category.valueOf(category));
        budget.setDescription(description);
               budgetRepository.save(budget);
        return "redirect:/budget";
    }

    @GetMapping("/budget/{id}")
    public String budgetEdit (@PathVariable ("id") Long id,
    Model model) {
        if (!budgetRepository.existsById(id)){
            return "budget";
        }
        Optional<Budget> budget = budgetRepository.findById(id);
        ArrayList<Budget> result = new ArrayList<>();
       budget.ifPresent(result::add);
       model.addAttribute("budget", result);
        return "budget-edit";
    }

    @PostMapping("/budget/{id}")
    public String budgetUpdate(@PathVariable("id") Long id, @RequestParam Integer amount,  @RequestParam String description,
                                @ModelAttribute Budget budget, Model model) {
        Budget databaseBudget = budgetRepository.findById(id).orElseThrow();
       databaseBudget.setAmount(amount);
       databaseBudget.setDescription(description);
       budgetRepository.save(databaseBudget);
        return "redirect:/budget";
    }

    @PostMapping("/budget/{id}/remove")
    public String budgetDelete (@PathVariable("id") Long id, Model model) {
        Budget budgets = budgetRepository.findById(id).orElseThrow();
       budgetRepository.delete(budgets);
        return "redirect:/budget";
    }

}
