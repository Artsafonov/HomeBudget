package com.example.homebudget.controllers;

import com.example.homebudget.entity.RevenueCategory;
import com.example.homebudget.models.CategoryWithRevenueAmount;
import com.example.homebudget.models.Revenue;
import com.example.homebudget.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;


@Controller
public class RevenueController {
    @Autowired
    private RevenueRepository revenueRepository;
    private Model model;

    @GetMapping("/revenue")
    public String revenue(Model model) {
        List<Revenue> revenues = (List<Revenue>) revenueRepository.findAll();
        Map<String, CategoryWithRevenueAmount> chartDataRaw = new HashMap<>();
        for (Revenue revenue : revenues) {
            CategoryWithRevenueAmount categoryWithRevenueAmount = chartDataRaw.get(revenue.getRevenueCategory().name());
            if (categoryWithRevenueAmount == null) {
                categoryWithRevenueAmount = new CategoryWithRevenueAmount();
                categoryWithRevenueAmount.setRevenueCategory(revenue.getRevenueCategory().name());
                chartDataRaw.put(revenue.getRevenueCategory().name(), categoryWithRevenueAmount);
            }
            categoryWithRevenueAmount.setTotalRevenueAmount(categoryWithRevenueAmount.getTotalRevenueAmount() + revenue.getRevenueAmount());
            Collection<CategoryWithRevenueAmount> values = chartDataRaw.values();
            List<List> listWithData = new ArrayList<>();
            values.forEach(x -> {
                List objectList = new ArrayList();
                objectList.add(x.getRevenueCategory());
                objectList.add(x.getTotalRevenueAmount());
                listWithData.add(objectList);
            });
            model.addAttribute("chartRevenueData", listWithData);
        }

        model.addAttribute("revenues", revenues);
        model.addAttribute("revenueCategories", RevenueCategory.values());
        return "revenue";
    }

    @PostMapping("/revenue")
    public String revenueAdd(@RequestParam String revenueCategory, @RequestParam Integer revenueAmount, @RequestParam String revenueDescription,
                             Model model) {
        Revenue revenue = new Revenue(revenueCategory, revenueAmount, revenueDescription);
        revenue.setDate(LocalDate.now());
        revenue.setRevenueAmount(revenueAmount);
        revenue.setRevenueCategory(RevenueCategory.valueOf(revenueCategory));
        revenue.setRevenueDescription(revenueDescription);
        revenueRepository.save(revenue);
        return "redirect:/budget";
    }

}

