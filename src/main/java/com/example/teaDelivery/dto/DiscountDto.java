package com.example.teaDelivery.dto;

public class DiscountDto extends BaseDto{
    private String discountName;
    private String description;
    private String teaSort;
    private double discount;
    private double loyalCost;

    public DiscountDto(Long id, String discountName, String description, String teaSort, double discount, double loyalCost) {
        setId(id);
        this.discountName = discountName;
        this.description = description;
        this.teaSort = teaSort;
        this.discount = discount;
        this.loyalCost = loyalCost;
    }

    public DiscountDto() {

    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeaSort() {
        return teaSort;
    }

    public void setTeaSort(String teaSort) {
        this.teaSort = teaSort;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getLoyalCost() {
        return loyalCost;
    }

    public void setLoyalCost(double loyalCost) {
        this.loyalCost = loyalCost;
    }
}
