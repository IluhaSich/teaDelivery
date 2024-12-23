package com.example.teaDelivery.service;

import com.example.pr6c.viewmodel.base.BaseViewModel;
import com.example.pr6c.viewmodel.tea.TeaViewModel;
import com.example.teaDelivery.dto.TeaDto;
import com.example.teaDelivery.dto.TeaInOrderDto;
import com.example.teaDelivery.models.entity.Tea;
import com.example.teaDelivery.models.entity.TeaInOrder;
import com.example.teaDelivery.repository.TeaInOrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TeaInOrderService implements BaseService<TeaInOrderDto, TeaInOrder> {
    private final TeaInOrderRepository teaInOrderRepository;
    private final TeaService teaService;
    private final IngredientService ingredientService;
    private final SupplierService supplierService;

    public TeaInOrderService(TeaInOrderRepository teaInOrderRepository, TeaService teaService, IngredientService ingredientService, SupplierService supplierService) {
        this.teaInOrderRepository = teaInOrderRepository;
        this.teaService = teaService;
        this.ingredientService = ingredientService;
        this.supplierService = supplierService;
    }

    public List<TeaInOrderDto> getTeasInOrder(Long id){
        return teaInOrderRepository.getById(id).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public TeaInOrderDto convertToDto(TeaInOrder teaInOrder) {
        return new TeaInOrderDto(
                teaInOrder.getTeaOrder().getId(),
                teaInOrder.getTea().getId());
    }

    private TeaViewModel convertToTeaViewModel(TeaDto teaDto) {
        return new TeaViewModel(
                new BaseViewModel("",""),
                teaDto.getId(),
                teaDto.getImage(),
                teaDto.getName(),
                teaDto.getDescription(),
                teaDto.getSort(),
                ingredientService.getIngredientsByTeaId(teaDto.getId()),
                teaDto.getCost(),
                teaDto.isAvailability(),
                supplierService.getSupplierById(teaDto.getSuppliers()).getSupplier_name(),
                false
        );
    }


    public List<TeaViewModel> getTeasByOrderId(Long id) {
        List<TeaInOrder> teaInOrderList = teaInOrderRepository.findByTeaOrderId(id);

        if (teaInOrderList == null || teaInOrderList.isEmpty()) {
            return new ArrayList<>();
        }

        List<Tea> teaList = teaInOrderList.stream()
                .map(TeaInOrder::getTea)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<TeaDto> teaDtoList = teaList.stream()
                .map(teaService::convertToDto)
                .collect(Collectors.toList());

        List<TeaViewModel> teaViewModels = teaDtoList.stream()
                .map(this::convertToTeaViewModel)
                .collect(Collectors.toList());

        return teaViewModels;
    }


}
