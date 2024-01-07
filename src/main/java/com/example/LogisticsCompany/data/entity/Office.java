package com.example.LogisticsCompany.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "office")
public class Office extends BaseEntity {
    @NotBlank
    private String name;

    @NotBlank
    private String location;

    private Double revenue;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "office")
    private List<Employee> employees;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "office")
    private List<Shipment> shipments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "office")
    private List<Client> clients;
}
