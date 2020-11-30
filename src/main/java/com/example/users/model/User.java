package com.example.users.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@IdClass(UniqueOrder.class)
public class User implements Serializable {

    private static Long serialVersionUID = 1L;

    @Id
    @Column(name = "id",nullable = false)
    @NotBlank(message = "Id is mandatory")
    private String id;

    @Column(name = "name",nullable = false)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Id
    @Column(name = "login",nullable = false)
    @NotBlank(message = "Login is mandatory")
    private String login;

    @Column(name = "salary",nullable = false)
    @NotBlank(message = "Salary is mandatory")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private double salary;

    @Column(name = "startDate",nullable = false)
    @NotBlank(message = "Start Date is mandatory")
    private LocalDate startDate;

}
