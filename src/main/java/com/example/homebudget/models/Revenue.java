package com.example.homebudget.models;

import com.example.homebudget.entity.RevenueCategory;
import com.example.homebudget.entity.User;

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
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User username;


    public Revenue() {
    }


    public Revenue(String revenueCategory, Integer revenueAmount, String revenueDescription, User user) {
        this.username = user;
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

    public void setUsername(User username) {
        this.username = username;
    }
    public String getUsername(){
        return username !=null ? username.getUsername() : "none";
    }
}
