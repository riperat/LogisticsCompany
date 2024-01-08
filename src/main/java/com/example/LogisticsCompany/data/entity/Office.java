package com.example.LogisticsCompany.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @Min(value = 0, message = "Minimum value is 0")
    @Max(value = 100000, message = "Too big for us")
    private Double revenue = 0.0;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "office", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "office", cascade = CascadeType.ALL)
    private List<Shipment> shipments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "office", cascade = CascadeType.ALL)
    private List<Client> clients;
}
