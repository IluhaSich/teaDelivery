package com.example.teaDelivery.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "discount_client")
public class UserDiscount extends BaseEntity {
    private Discount discount;
    private User user;
    private boolean wasUsed;

    public UserDiscount() {
    }

    public UserDiscount(Discount discount, User user, boolean wasUsed) {
        this.discount = discount;
        this.user = user;
        this.wasUsed = wasUsed;
    }

    @ManyToOne
    @JoinColumn(name = "personal_discount_id")
    public Discount getPersonalDiscount() {
        return discount;
    }

    @ManyToOne
    @JoinColumn(name = "client_id")
    public User getUser() {
        return user;
    }

    @Column(name = "was_used")
    public boolean isWasUsed() {
        return wasUsed;
    }

    public void setPersonalDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setWasUsed(boolean wasUsed) {
        this.wasUsed = wasUsed;
    }
}
