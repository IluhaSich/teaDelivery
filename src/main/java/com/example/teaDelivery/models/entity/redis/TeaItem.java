package com.example.teaDelivery.models.entity.redis;

import java.io.Serializable;

public class TeaItem implements Serializable {
    private Long teaId;
    private String sort;
    private String name;
    private double cost;
    private double discountedCost;
    private int quantity;

    public TeaItem() {
    }

    public TeaItem(Long teaId, String sort, String name, double cost, double discountedCost, int quantity) {
        this.teaId = teaId;
        this.sort = sort;
        this.name = name;
        this.cost = cost;
        this.discountedCost = discountedCost;
        this.quantity = quantity;
    }

    public Long getTeaId() {
        return teaId;
    }

    public void setTeaId(Long teaId) {
        this.teaId = teaId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDiscountedCost() {
        return discountedCost;
    }

    public void setDiscountedCost(double discountedCost) {
        this.discountedCost = discountedCost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
