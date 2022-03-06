package com.example.homebudget.service;

import com.example.homebudget.models.Budget;
import com.example.homebudget.models.Revenue;
import com.example.homebudget.repository.BudgetRepository;
import com.example.homebudget.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TotalBalanceService {
    @Autowired
    BudgetRepository budgetRepository;
    @Autowired
    RevenueRepository revenueRepository;

    public List<List> prepareTotalBalanceData(){

        Integer incomeSum = ((List<Budget>) budgetRepository.findAll()).stream().mapToInt(x -> x.getAmount()).sum();
        Integer outcomeSum = ((List<Revenue>)revenueRepository.findAll()).stream().mapToInt(x -> x.getRevenueAmount()).sum();

        return convertToJsFormat(incomeSum, outcomeSum);
    }

    private List<List> convertToJsFormat(Integer incomeSum, Integer outcomeSum) {
        List<List> listWithData = new ArrayList<>();
        listWithData.add(createSubList("INCOME", incomeSum));
        listWithData.add(createSubList("OUTCOME", outcomeSum));

        return listWithData;
    }

    private List createSubList(String label, Integer value) {
        List sublist = new ArrayList();
        sublist.add(label);
        sublist.add(value);
        return sublist;
    }

}
