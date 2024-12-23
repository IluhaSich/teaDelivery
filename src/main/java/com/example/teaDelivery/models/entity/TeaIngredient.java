package com.example.teaDelivery.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tea_ingredient")
public class TeaIngredient extends BaseEntity{
    private Tea teaId;
    private Ingredient ingredientId;

    @ManyToOne
    @JoinColumn(name = "tea_id")
    public Tea getTeaId() {
        return teaId;
    }

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    public Ingredient getIngredientId() {
        return ingredientId;
    }

    public void setTeaId(Tea teaId) {
        this.teaId = teaId;
    }

    public void setIngredientId(Ingredient ingredientId) {
        this.ingredientId = ingredientId;
    }
}
