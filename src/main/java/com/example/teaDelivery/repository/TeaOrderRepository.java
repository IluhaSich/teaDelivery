package com.example.teaDelivery.repository;

import com.example.teaDelivery.models.entity.TeaOrder;
import com.example.teaDelivery.models.entity.User;
import com.example.teaDelivery.models.enums.OrderStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeaOrderRepository extends BaseRepository<TeaOrder,Long>{
    Page<TeaOrder> findAll(Pageable pageable);
    Optional<TeaOrder> findTopByOrderByTimeDesc();
}
