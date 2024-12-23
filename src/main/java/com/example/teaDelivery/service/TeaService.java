package com.example.teaDelivery.service;

import com.example.teaDelivery.dto.TeaDto;
import com.example.teaDelivery.models.entity.Tea;
import com.example.teaDelivery.models.entity.TeaInOrder;
import com.example.teaDelivery.models.entity.TeaOrder;
import com.example.teaDelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeaService implements BaseService<TeaDto, Tea> {
    private final TeaRepository teaRepository;
    private final TeaInOrderRepository teaInOrderRepository;
    private final TeaIngredientRepository teaIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final TeaOrderRepository teaOrderRepository;

    public TeaService(TeaRepository teaRepository, TeaInOrderRepository teaInOrderRepository, TeaIngredientRepository teaIngredientRepository, IngredientRepository ingredientRepository, TeaOrderRepository teaOrderRepository) {
        this.teaRepository = teaRepository;
        this.teaInOrderRepository = teaInOrderRepository;
        this.teaIngredientRepository = teaIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.teaOrderRepository = teaOrderRepository;
    }

    public Page<TeaDto> getAllTea(String name, String sort, int startCost, int endCost, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("name"));
        Page<Tea> teas = name != null
                ? teaRepository.findByNameContainingIgnoreCaseAndSortContainingIgnoreCaseAndCostGreaterThanAndCostLessThan
                (name, sort, startCost, endCost, pageable)
                : teaRepository.findAll(pageable);
        return teas.map(tea -> new TeaDto(
                tea.getId(),
                tea.getSort(),
                tea.getName(),
                tea.getImage(),
                tea.getCost(),
                tea.getDescription(),
                tea.getSuppliers().getId(),
                tea.isAvailability()
        ));
    }

    public List<TeaDto> getBySort(String sort) {
        return teaRepository.getBySort(sort).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public TeaDto getTeaById(Long id) {
        return convertToDto(teaRepository.getById(id).orElseThrow(() -> new RuntimeException("Чай не найден")));
    }


    public TeaDto getLastTea() {
        Optional<TeaOrder> lastOrder = teaOrderRepository.findTopByOrderByTimeDesc();
        Optional<TeaInOrder> teaInOrder = teaInOrderRepository.findFirstByTeaOrderOrderByIdAsc(lastOrder.get());
        return convertToDto(teaInOrder.map(TeaInOrder::getTea).orElseThrow());
    }

    public List<String> getAllSorts() {
        return teaRepository.findAllDistinctSort();
    }

    @Override
    public TeaDto convertToDto(Tea tea) {
        TeaDto teaDto = new TeaDto();
        teaDto.setId(tea.getId());
        teaDto.setSort(tea.getSort());
        teaDto.setName(tea.getName());
        teaDto.setImage(tea.getImage());
        teaDto.setCost(tea.getCost());
        teaDto.setDescription(tea.getDescription());
        teaDto.setSuppliers(tea.getSuppliers().getId());
        teaDto.setAvailability(tea.isAvailability());
        return teaDto;
    }
}
