package com.example.LogisticsCompany.web.dto;

import com.example.LogisticsCompany.data.entity.Client;
import com.example.LogisticsCompany.data.entity.Employee;
import com.example.LogisticsCompany.data.entity.Office;
import com.example.LogisticsCompany.data.entity.Shipment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateOfficeDTO {
    private String name;
    private String location;
    private Double revenue;
    private List<Employee> employees;
    private List<Shipment> shipments;
    private List<Client> clients;
    private Office office;
}
