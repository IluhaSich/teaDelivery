package com.example.teaDelivery.dto;

import java.time.LocalDate;

public class UserDto extends BaseDto{
    private String name;
    private LocalDate registrationDate;
    private String email;
    private String password;
    private String phoneNumber;
    private int loyaltyPoints;


    public UserDto(){}

    public UserDto(Long id, String name, LocalDate registrationDate, String email, String password, String phoneNumber, int loyaltyPoints) {
        setId(id);
        this.name = name;
        this.registrationDate = registrationDate;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.loyaltyPoints = loyaltyPoints;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
