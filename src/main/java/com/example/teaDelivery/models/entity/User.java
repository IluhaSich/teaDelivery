package com.example.teaDelivery.models.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class User extends BaseEntity implements Serializable {
    private String username;

    private String password;

    private String email;

    private List<Role> roles;

    private String fullName;

    private LocalDate registrationDate;

    private String phoneNumber;

    private int loyaltyPoints;

    public User() {
        this.roles = new ArrayList<>();
    }

    public User(String username, String password, String email, String fullName, LocalDate registrationDate, String phoneNumber, int loyaltyPoints) {
        this();
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.registrationDate = registrationDate;
        this.phoneNumber = phoneNumber;
        this.loyaltyPoints = loyaltyPoints;
    }

    @Column(name = "client_username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    @Column(name = "registration_date")
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    @Column(name = "client_email", unique = true)
    public String getEmail() {
        return email;
    }

    @Column(name = "client_password", nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "user_role")
    @ManyToMany(fetch = FetchType.EAGER)
    public List<Role> getRoles() {
        return roles;
    }
    @Column(name = "user_fullname",nullable = false)
    public String getFullName() {
        return fullName;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Column(name = "loyalty_points")
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", fullName='" + fullName + '\'' +
                ", registrationDate=" + registrationDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                '}';
    }
}

