package com.example.teaDelivery.repository;

import com.example.teaDelivery.models.entity.Discount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends BaseRepository<Discount,Long>{
    Discount getByDiscountName(String discountName);

}
