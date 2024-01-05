package com.example.LogisticsCompany.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "repairman")
public class Employee extends BaseEntity {

    @NotBlank
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyID")
    private Company company;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Shipment> shipments;

}
