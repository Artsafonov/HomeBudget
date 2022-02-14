package com.example.homebudget.models;

public class CategoryWithRevenueAmount {
    private String RevenueCategory;
    private long totalRevenueAmount;

    public String getRevenueCategory() {
        return RevenueCategory;
    }

    public void setRevenueCategory(String revenueCategory) {
        RevenueCategory = revenueCategory;
    }

    public long getTotalRevenueAmount() {
        return totalRevenueAmount;
    }

    public void setTotalRevenueAmount(long totalRevenueAmount) {
        this.totalRevenueAmount = totalRevenueAmount;
    }
}
