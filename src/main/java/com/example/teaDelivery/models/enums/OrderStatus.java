package com.example.teaDelivery.models.enums;

public enum OrderStatus {
    ORDERED(0),PACKED(1),TRANSIT(2),DELIVERED(3);

    private int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
