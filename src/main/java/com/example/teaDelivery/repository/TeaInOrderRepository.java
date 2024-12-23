package com.example.teaDelivery.repository;

import com.example.teaDelivery.models.entity.Tea;
import com.example.teaDelivery.models.entity.TeaInOrder;
import com.example.teaDelivery.models.entity.TeaOrder;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeaInOrderRepository extends BaseRepository<TeaInOrder, Long> {
    @Query("SELECT tio FROM TeaInOrder tio WHERE tio.teaOrder.id = :orderId")
    List<TeaInOrder> findByTeaOrderId(@Param("orderId") Long orderId);
    Optional<TeaInOrder> findFirstByTeaOrderOrderByIdAsc(TeaOrder teaOrder);
}
