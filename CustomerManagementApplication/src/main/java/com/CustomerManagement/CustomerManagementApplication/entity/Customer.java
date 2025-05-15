package com.CustomerManagement.CustomerManagementApplication.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Email;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @Email(message = "Enter valid Email")
    private String email;

    private Double annualSpend;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastPurchedDate;

}
