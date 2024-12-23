package com.example.teaDelivery.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "personal_discount")
public class Discount extends BaseEntity {
    private String discountName;
    private String description;
    private String teaSort;
    private double discount;
    private double loyalCost;

    public Discount() {
    }

    public Discount(String discountName, String description, String teaSort, double discount, double loyalCost) {
        this.discountName = discountName;
        this.description = description;
        this.teaSort = teaSort;
        this.discount = discount;
        this.loyalCost = loyalCost;
    }

    @Column(name = "discount_name")
    public String getDiscountName() {
        return discountName;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @Column(name = "tea_sort")
    public String getTeaSort() {
        return teaSort;
    }

    @Column(name = "discount")
    public double getDiscount() {
        return discount;
    }

    @Column(name = "loyal_cost")
    public double getLoyalCost() {
        return loyalCost;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTeaSort(String teaSort) {
        this.teaSort = teaSort;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setLoyalCost(double loyalCost) {
        this.loyalCost = loyalCost;
    }
}

