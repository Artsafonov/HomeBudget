package com.example.homebudget.models;

import com.example.homebudget.entity.Category;
import com.example.homebudget.entity.RevenueCategory;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "revenue")
public class Revenue {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RevenueCategory revenueCategory;
    private String revenueDescription;
    private LocalDate date;
    private Integer revenueAmount;


    public Revenue() {
    }


    public Revenue(Long id, RevenueCategory revenueCategory, String revenueDescription, LocalDate date, Integer revenueAmount) {
        this.id = id;
        this.revenueCategory = revenueCategory;
        this.revenueDescription = revenueDescription;
        this.date = date;
        this.revenueAmount = revenueAmount;
    }

    public Revenue(String revenueCategory, Integer revenueAmount, String revenueDescription) {
        this.revenueCategory = RevenueCategory.valueOf(revenueCategory);
        this.revenueAmount= revenueAmount;
        this.revenueDescription = revenueDescription;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RevenueCategory getRevenueCategory() {
        return revenueCategory;
    }

    public void setRevenueCategory(RevenueCategory revenueCategory) {
        this.revenueCategory = revenueCategory;
    }

    public String getRevenueDescription() {
        return revenueDescription;
    }

    public void setRevenueDescription(String revenueDescription) {
        this.revenueDescription = revenueDescription;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRevenueAmount() {
        return revenueAmount;
    }

    public void setRevenueAmount(Integer revenueAmount) {
        this.revenueAmount = revenueAmount;
    }
}
