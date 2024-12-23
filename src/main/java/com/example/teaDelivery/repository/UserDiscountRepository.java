package com.example.teaDelivery.repository;

import com.example.teaDelivery.models.entity.Discount;
import com.example.teaDelivery.models.entity.UserDiscount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDiscountRepository extends BaseRepository<UserDiscount,Long>{
    @Query("SELECT ud FROM UserDiscount ud WHERE ud.user.id = :userId")
    List<UserDiscount> findByUserId(@Param("userId") Long userId);
}
