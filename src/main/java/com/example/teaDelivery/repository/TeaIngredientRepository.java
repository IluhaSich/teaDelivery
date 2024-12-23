package com.example.teaDelivery.repository;

import com.example.teaDelivery.models.entity.Tea;
import com.example.teaDelivery.models.entity.TeaIngredient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeaIngredientRepository extends BaseRepository<TeaIngredient,Long>{
      List<TeaIngredient> getByTeaId(Tea teaId);
}
