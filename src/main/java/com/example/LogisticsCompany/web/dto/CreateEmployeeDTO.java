package com.example.LogisticsCompany.web.dto;

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
public class CreateEmployeeDTO {
    private String name;
    private Set<Shipment> shipments;
}
