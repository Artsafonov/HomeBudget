package com.example.homebudget.controllers;

import com.example.homebudget.entity.RevenueCategory;
import com.example.homebudget.entity.User;
import com.example.homebudget.models.Budget;
import com.example.homebudget.models.CategoryWithRevenueAmount;
import com.example.homebudget.models.Revenue;
import com.example.homebudget.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@Controller
public class RevenueController {
    @Autowired
    private RevenueRepository revenueRepository;


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
            model.addAttribute("chartData", listWithData);
        }
        model.addAttribute("totalRevenueAmount", revenues.stream().mapToInt(x -> x.getRevenueAmount()).sum());
        model.addAttribute("revenues", revenues);
        model.addAttribute("revenueCategories", RevenueCategory.values());
        return "revenue";
    }

    @PostMapping("/revenue")
    public String revenueAdd(
            @AuthenticationPrincipal User user,
            @RequestParam String revenueCategory,
            @RequestParam Integer revenueAmount,
            @RequestParam String revenueDescription,
            Model model) {
        Revenue revenue = new Revenue(revenueCategory, revenueAmount, revenueDescription, user);
        revenue.setDate(LocalDate.now());
        revenue.setRevenueAmount(revenueAmount);
        revenue.setRevenueCategory(RevenueCategory.valueOf(revenueCategory));
        revenue.setRevenueDescription(revenueDescription);
        revenueRepository.save(revenue);
        return "redirect:/revenue";
    }

    @GetMapping("/revenue/{id}")
    public String revenueEdit(@PathVariable("id") Long id,
                              Model model) {
        if (!revenueRepository.existsById(id)) {
            return "revenue";
        }
        Optional<Revenue> revenue = revenueRepository.findById(id);
        ArrayList<Revenue> result = new ArrayList<>();
        revenue.ifPresent(result::add);
        model.addAttribute("revenue", result);
        return "revenue-edit";
    }

    @PostMapping("/revenue/{id}")
    public String revenueUpdate(@PathVariable("id") Long id, @RequestParam Integer amount, @RequestParam String description,
                                @ModelAttribute Revenue revenue, Model model) {
        Revenue databaseRevenue = revenueRepository.findById(id).orElseThrow();
        databaseRevenue.setRevenueAmount(amount);
        databaseRevenue.setRevenueDescription(description);
        revenueRepository.save(databaseRevenue);
        return "redirect:/revenue";
    }

    @PostMapping("/revenue/{id}/remove")
    public String revenueDelete(@PathVariable("id") Long id, Model model) {
        Revenue revenue = revenueRepository.findById(id).orElseThrow();
        revenueRepository.delete(revenue);
        return "redirect:/revenue";
    }


}

