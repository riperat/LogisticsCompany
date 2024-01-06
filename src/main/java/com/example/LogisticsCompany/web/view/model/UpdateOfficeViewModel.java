package com.example.LogisticsCompany.web.view.model;

import com.example.LogisticsCompany.data.entity.Client;
import com.example.LogisticsCompany.data.entity.Employee;
import com.example.LogisticsCompany.data.entity.Shipment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateOfficeViewModel {
    private String name;
    private Double revenue;
    private Set<Employee> employees;
    private Set<Shipment> shipments;
    private Set<Client> clients;
}
