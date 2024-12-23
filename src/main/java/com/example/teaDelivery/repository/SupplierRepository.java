package com.example.teaDelivery.repository;

import com.example.teaDelivery.models.entity.Supplier;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends BaseRepository<Supplier,Long> {
}
