package com.example.teaDelivery.dto;

public class UserDiscountDto extends BaseDto{
    private Long personalDiscount;
    private Long user;
    private boolean wasUsed;

    public UserDiscountDto(Long id, Long personalDiscount, Long user, boolean wasUsed) {
        setId(id);
        this.personalDiscount = personalDiscount;
        this.user = user;
        this.wasUsed = wasUsed;
    }

    public Long getPersonalDiscount() {
        return personalDiscount;
    }

    public void setPersonalDiscount(Long personalDiscount) {
        this.personalDiscount = personalDiscount;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public boolean isWasUsed() {
        return wasUsed;
    }

    public void setWasUsed(boolean wasUsed) {
        this.wasUsed = wasUsed;
    }
}
