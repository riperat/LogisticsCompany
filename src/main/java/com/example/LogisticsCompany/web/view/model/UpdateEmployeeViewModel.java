package com.example.LogisticsCompany.web.view.model;

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
public class UpdateEmployeeViewModel {
    private String name;
    private Set<Shipment> shipments;
}
