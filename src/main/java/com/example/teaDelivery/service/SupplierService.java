package com.example.teaDelivery.service;

import com.example.teaDelivery.dto.SupplierDto;
import com.example.teaDelivery.models.entity.Supplier;
import com.example.teaDelivery.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService implements BaseService<SupplierDto, Supplier> {
    SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public SupplierDto getSupplierById(Long id) {
        return convertToDto(supplierRepository.getById(id).orElseThrow(() -> new RuntimeException("Поставщик не найден")));
    }


    @Override
    public SupplierDto convertToDto(Supplier supplier) {
        SupplierDto supplierDto = new SupplierDto(
                supplier.getId(),
                supplier.getSupplier_name(),
                supplier.getEmail()
        );
        return supplierDto;
    }
}
