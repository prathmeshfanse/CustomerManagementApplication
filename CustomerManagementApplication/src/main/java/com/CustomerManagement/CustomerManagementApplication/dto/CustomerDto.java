package com.CustomerManagement.CustomerManagementApplication.dto;

import java.util.Date;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerDto {

    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    private Double annualSpend;
    private Date lastPurchedDate;
    private String tier;

    public CustomerDto(UUID id, String name, String email, Double annualSpend, Date lastPurchedDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.annualSpend = annualSpend;
        this.lastPurchedDate = lastPurchedDate;
        this.tier = tier;

    }

    public CustomerDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getAnnualSpend() {
        return annualSpend;
    }

    public void setAnnualSpend(Double annualSpend) {
        this.annualSpend = annualSpend;
    }

    public Date getLastPurchedDate() {
        return lastPurchedDate;
    }

    public void setLastPurchedDate(Date lastPurchedDate) {
        this.lastPurchedDate = lastPurchedDate;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

}
