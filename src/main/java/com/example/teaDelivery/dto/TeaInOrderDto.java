package com.example.teaDelivery.dto;

public class TeaInOrderDto {
    private Long teaOrder;
    private Long tea;

    public TeaInOrderDto(Long teaOrder, Long tea) {
        this.teaOrder = teaOrder;
        this.tea = tea;
    }

    public Long getTea() {
        return tea;
    }

    public void setTea(Long tea) {
        this.tea = tea;
    }

    public Long getTeaOrder() {
        return teaOrder;
    }

    public void setTeaOrder(Long teaOrder) {
        this.teaOrder = teaOrder;
    }
}
