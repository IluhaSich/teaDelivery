package com.example.teaDelivery.dto;

public class TeaIngredientDto extends BaseDto{
    private Long teaId;
    private Long ingredientId;

    public TeaIngredientDto(Long teaId, Long ingredientId) {
        this.teaId = teaId;
        this.ingredientId = ingredientId;
    }

    public Long getTeaId() {
        return teaId;
    }

    public void setTeaId(Long teaId) {
        this.teaId = teaId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
}
