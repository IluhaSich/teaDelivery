package com.example.teaDelivery.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tea_in_order")
public class TeaInOrder extends BaseEntity{
    private TeaOrder teaOrder;
    private Tea tea;

    public TeaInOrder() {
    }

    public TeaInOrder(TeaOrder teaOrder, Tea tea) {
        this.teaOrder = teaOrder;
        this.tea = tea;
    }

    @ManyToOne
    @JoinColumn(name = "tea_order_id")
    public TeaOrder getTeaOrder() {
        return teaOrder;
    }

    @ManyToOne
    @JoinColumn(name = "tea_id")
    public Tea getTea() {
        return tea;
    }

    public void setTeaOrder(TeaOrder teaOrder) {
        this.teaOrder = teaOrder;
    }

    public void setTea(Tea tea) {
        this.tea = tea;
    }
}
