package com.example.teaDelivery.repository;

import com.example.teaDelivery.models.entity.Ingredient;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends BaseRepository<Ingredient,Long>{
}
