package com.example.homebudget.models;

import com.example.homebudget.entity.Category;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "budget")
public class Budget {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;
    private String description;
    private LocalDate date;
    private Integer amount;

    public Budget() {
    }

    public Budget(String category, Integer amount, String description) {
        this.category= Category.valueOf(category);
        this.amount=amount;
        this.description=description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}




