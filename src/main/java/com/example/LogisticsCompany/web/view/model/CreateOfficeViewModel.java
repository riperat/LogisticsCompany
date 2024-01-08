package com.example.LogisticsCompany.web.view.model;

import com.example.LogisticsCompany.data.entity.Client;
import com.example.LogisticsCompany.data.entity.Employee;
import com.example.LogisticsCompany.data.entity.Shipment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateOfficeViewModel {
    @NotBlank
    private String name;

    @NotBlank
    private String location;
    private Double revenue = 0.0;
    private Set<Employee> employees;
    private Set<Shipment> shipments;
    private Set<Client> clients;
}
