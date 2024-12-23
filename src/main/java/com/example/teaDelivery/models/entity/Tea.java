package com.example.teaDelivery.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tea")
public class Tea extends BaseEntity {
    private String sort;
    private String name;
    private String image;
    private double cost;
    private String description;
    private Supplier suppliers;
    private boolean availability;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    public Supplier getSuppliers() {
        return suppliers;
    }

    @Column(name = "sort")
    public String getSort() {
        return sort;
    }

    @Column(name = "tea_name")
    public String getName() {
        return name;
    }

    @Column(name = "image")
    public String getImage() {
        return image;
    }

    @Column(name = "tea_cost")
    public double getCost() {
        return cost;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @Column(name = "availability")
    public boolean isAvailability() {
        return availability;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSuppliers(Supplier suppliers) {
        this.suppliers = suppliers;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}

