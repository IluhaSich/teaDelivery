package com.example.teaDelivery.models.entity;

import com.example.teaDelivery.models.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tea_order")
public class TeaOrder extends BaseEntity {
    private User user;
    private LocalDateTime time;
    private Discount discount;
    private double cost;
    private LocalDateTime deliveryDate;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryStreet;
    private String deliveryZip;
    private OrderStatus orderStatus;

    public TeaOrder() {
    }

    public TeaOrder(User user, LocalDateTime time, Discount discount, double cost, LocalDateTime deliveryDate, String deliveryCity, String deliveryState, String deliveryStreet, String deliveryZip, OrderStatus orderStatus1) {
        this.user = user;
        this.time = time;
        this.discount = discount;
        this.cost = cost;
        this.deliveryDate = deliveryDate;
        this.deliveryCity = deliveryCity;
        this.deliveryState = deliveryState;
        this.deliveryStreet = deliveryStreet;
        this.deliveryZip = deliveryZip;
        this.orderStatus = orderStatus1;
    }

    @ManyToOne
    @JoinColumn(name = "client_id")
    public User getClient() {
        return user;
    }


    @ManyToOne
    @JoinColumn(name = "discount_id")
    public Discount getPersonalDiscount() {
        return discount;
    }
    @Column(name = "order_time")
    public LocalDateTime getTime() {
        return time;
    }

    @Column(name = "order_cost")
    public double getCost() {
        return cost;
    }

    @Column(name = "delivery_date")
    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    @Column(name = "delivery_city")
    public String getDeliveryCity() {
        return deliveryCity;
    }

    @Column(name = "delivery_state")
    public String getDeliveryState() {
        return deliveryState;
    }

    @Column(name = "delivery_street")
    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    @Column(name = "delivery_zip")
    public String getDeliveryZip() {
        return deliveryZip;
    }

    @Column(name = "delivery_status")
    @Enumerated(EnumType.STRING)
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setClient(User user) {
        this.user = user;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setPersonalDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public void setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
    }

    public void setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
