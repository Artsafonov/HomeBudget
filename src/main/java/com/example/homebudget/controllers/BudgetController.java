package com.example.homebudget.controllers;

import com.example.homebudget.entity.Category;
import com.example.homebudget.entity.User;
import com.example.homebudget.models.Budget;
import com.example.homebudget.repository.BudgetRepository;
import com.example.homebudget.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class BudgetController {
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetService budgetService;

    @GetMapping("/budget")
    public String budget(Model model) {
        List<Budget> budgets = (List<Budget>) budgetRepository.findAll();
        model.addAttribute("chartData", budgetService.prepareChartData(budgets));
        model.addAttribute("totalAmount", budgets.stream().mapToInt(x -> x.getAmount()).sum());
        model.addAttribute("budgets", budgets);
        model.addAttribute("categories", Category.values());
        return "budget";
    }

    //formFieldName:formFieldValue&formField2Name:formFieldValue2
    @PostMapping("/budget")
    public String budgetAdd(
            @AuthenticationPrincipal User user,
            @RequestParam String category,
            @RequestParam Integer amount,
            @RequestParam String description,
            Model model) {
        Budget budget = new Budget(category, amount, description, user);
        budget.setDate(LocalDate.now());
        budget.setAmount(amount);
        budget.setCategory(Category.valueOf(category));
        budget.setDescription(description);
        budgetRepository.save(budget);
        return "redirect:/budget";
    }

    @GetMapping("/budget/{id}")
    public String budgetEdit(@PathVariable("id") Long id,
                             Model model) {
        if (!budgetRepository.existsById(id)) {
            return "budget";
        }
        Optional<Budget> budget = budgetRepository.findById(id);
        ArrayList<Budget> result = new ArrayList<>();
        budget.ifPresent(result::add);
        model.addAttribute("budget", result);
        return "budget-edit";
    }

    @PostMapping("/budget/{id}")
    public String budgetUpdate(@PathVariable("id") Long id, @RequestParam Integer amount, @RequestParam String description,
                               @ModelAttribute Budget budget, Model model) {
        Budget databaseBudget = budgetRepository.findById(id).orElseThrow();
        databaseBudget.setAmount(amount);
        databaseBudget.setDescription(description);
        budgetRepository.save(databaseBudget);
        return "redirect:/budget";
    }

    @PostMapping("/budget/{id}/remove")
    public String budgetDelete(@PathVariable("id") Long id, Model model) {
        Budget budgets = budgetRepository.findById(id).orElseThrow();
        budgetRepository.delete(budgets);
        return "redirect:/budget";
    }

}
