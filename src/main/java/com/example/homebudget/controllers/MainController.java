package com.example.homebudget.controllers;

import com.example.homebudget.service.TotalBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class MainController {

    @Autowired
    private TotalBalanceService totalBalanceService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home page");
        return "homePage";
    }

    @GetMapping("/budgetHome")
    public String budgetHome(Model model) {
        model.addAttribute("title", "BudgetHomePage");
        model.addAttribute("chartData", totalBalanceService.prepareTotalBalanceData());
        return "budgetHome";
    }

}
