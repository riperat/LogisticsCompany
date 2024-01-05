package com.example.LogisticsCompany.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shipment")
public class Shipment extends BaseEntity {

    @NotBlank
    private String name;

    @NotBlank
    @Min(value = 1950, message = "Min 1950")
    private String weight;

    @NotBlank
    private String price;

    @NotBlank
    private String deliveryAddress;

    @NotBlank
    private boolean isDeliveredToOffice;

    @NotBlank
    private boolean isReceived;

    @NotBlank
    private boolean isSend;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<User> recipient;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<User> sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officeID")
    private Office office;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID")
    private Employee employee;
}
