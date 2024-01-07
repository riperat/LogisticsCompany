package com.example.LogisticsCompany.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shipment")
public class Shipment extends BaseEntity {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    private Long weight;

    private Long price;

    @NotBlank
    private String deliveryAddress;

    private boolean isReceived;

    private boolean isSend;

    @NotBlank
    private String recipient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officeID")
    private Office office;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clientID")
    private Client client;
}
