package com.example.teaDelivery.dto;

public class TeaDto  extends BaseDto{
    private String sort;
    private String name;
    private String image;
    private double cost;
    private String description;
    private Long suppliers;
    private boolean availability;

    public TeaDto() {
    }

    public TeaDto(Long id,String sort, String name, String image, double cost, String description, Long suppliers, boolean availability) {
        setId(id);
        this.sort = sort;
        this.name = name;
        this.image = image;
        this.cost = cost;
        this.description = description;
        this.suppliers = suppliers;
        this.availability = availability;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Long suppliers) {
        this.suppliers = suppliers;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
