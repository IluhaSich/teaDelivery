package com.example.teaDelivery.models.entity.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Basket implements Serializable {
    private String userId;
    private double totalCost;
    private double discountedCost;
    private List<TeaItem> teas = new ArrayList<>();

    public Basket(String userId, double totalCost, double discountedCost, List<TeaItem> teas) {
        this.userId = userId;
        this.totalCost = totalCost;
        this.discountedCost = discountedCost;
        this.teas = teas;
    }

    public Basket() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getDiscountedCost() {
        return discountedCost;
    }

    public void setDiscountedCost(double discountedCost) {
        this.discountedCost = discountedCost;
    }

    public List<TeaItem> getTeas() {
        return teas;
    }

    public void setTeas(List<TeaItem> teas) {
        this.teas = teas;
    }
}
