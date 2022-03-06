package com.example.homebudget.service;

import com.example.homebudget.models.Budget;
import com.example.homebudget.models.CategoryWithAmount;
import com.example.homebudget.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class BudgetService {

    public List<List> prepareChartData(List<Budget> budgets) {
        Map<String, CategoryWithAmount> chartDataRaw = new HashMap<>();
        for (Budget budget : budgets) {
            CategoryWithAmount categoryWithAmount = chartDataRaw.get(budget.getCategory().name());
            if (categoryWithAmount == null) {
                categoryWithAmount = new CategoryWithAmount();
                categoryWithAmount.setCategory(budget.getCategory().name());
                chartDataRaw.put(budget.getCategory().name(), categoryWithAmount);
            }
            categoryWithAmount.setTotalAmount(categoryWithAmount.getTotalAmount() + budget.getAmount());

        }
        List<List> listWithData = convertToJsFormat(chartDataRaw);
        return listWithData;

    }

    private List<List> convertToJsFormat(Map<String, CategoryWithAmount> chartDataRaw) {
        Collection<CategoryWithAmount> values = chartDataRaw.values();
        List<List> listWithData = new ArrayList<>();
        values.forEach(x -> {
            List objectList = new ArrayList();
            objectList.add(x.getCategory());
            objectList.add(x.getTotalAmount());
            listWithData.add(objectList);
        });
        return listWithData;
    }

    @Autowired
    private BudgetRepository budgetRepository;


}
