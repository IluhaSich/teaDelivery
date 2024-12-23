package com.example.teaDelivery.dto;

import java.time.LocalDateTime;

public class TeaOrderDto extends BaseDto{
    private Long client;
    private LocalDateTime time;
    private Long personalDiscount;
    private double cost;
    private LocalDateTime deliveryDate;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryStreet;
    private String deliveryZip;
    private String orderStatus;

    public TeaOrderDto(Long id,Long client, LocalDateTime time, Long personalDiscount, double cost,
                       LocalDateTime deliveryDate, String deliveryCity, String deliveryState,
                       String deliveryStreet, String deliveryZip, String orderStatus) {
        setId(id);
        this.client = client;
        this.time = time;
        this.personalDiscount = personalDiscount;
        this.cost = cost;
        this.deliveryDate = deliveryDate;
        this.deliveryCity = deliveryCity;
        this.deliveryState = deliveryState;
        this.deliveryStreet = deliveryStreet;
        this.deliveryZip = deliveryZip;
        this.orderStatus = orderStatus;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Long getPersonalDiscount() {
        return personalDiscount;
    }

    public void setPersonalDiscount(Long personalDiscount) {
        this.personalDiscount = personalDiscount;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    public void setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
    }

    public String getDeliveryZip() {
        return deliveryZip;
    }

    public void setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
