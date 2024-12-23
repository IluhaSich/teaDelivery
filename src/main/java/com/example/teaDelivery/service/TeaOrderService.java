package com.example.teaDelivery.service;

import com.example.teaDelivery.dto.TeaOrderDto;
import com.example.teaDelivery.models.entity.TeaOrder;
import com.example.teaDelivery.repository.TeaOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TeaOrderService implements BaseService<TeaOrderDto, TeaOrder> {
    private final TeaOrderRepository teaOrderRepository;

    public TeaOrderService(TeaOrderRepository teaOrderRepository) {
        this.teaOrderRepository = teaOrderRepository;
    }


    public Page<TeaOrderDto> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("time"));
        Page<TeaOrder> orders = teaOrderRepository.findAll(pageable);
        return orders.map(order -> new TeaOrderDto(
                order.getId(),
                order.getClient().getId(),
                order.getTime(),
                order.getPersonalDiscount().getId(),
                order.getCost(),
                order.getDeliveryDate(),
                order.getDeliveryCity(),
                order.getDeliveryState(),
                order.getDeliveryStreet(),
                order.getDeliveryZip(),
                order.getOrderStatus().name()
        ));
    }

    public TeaOrderDto getOrderById(Long id) {
        return convertToDto(teaOrderRepository.getById(id).orElseThrow(() -> new RuntimeException("Заказ не найден")));
    }


    @Override
    public TeaOrderDto convertToDto(TeaOrder teaOrder) {
        return new TeaOrderDto(
                teaOrder.getId(),
                teaOrder.getClient().getId(),
                teaOrder.getTime(),
                teaOrder.getPersonalDiscount().getId(),
                teaOrder.getCost(),
                teaOrder.getDeliveryDate(),
                teaOrder.getDeliveryCity(),
                teaOrder.getDeliveryState(),
                teaOrder.getDeliveryStreet(),
                teaOrder.getDeliveryZip(),
                teaOrder.getOrderStatus().name()
        );
    }
}
