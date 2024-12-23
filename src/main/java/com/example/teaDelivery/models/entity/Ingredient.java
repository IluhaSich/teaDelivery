package com.example.teaDelivery.models.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredient")
public class Ingredient extends BaseEntity{
    private String ingredient_name;

    public Ingredient() {
    }

    public Ingredient(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }
}