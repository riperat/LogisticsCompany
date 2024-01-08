package com.example.LogisticsCompany.web.dto;

import com.example.LogisticsCompany.data.entity.Office;
import com.example.LogisticsCompany.data.entity.Shipment;
import com.example.LogisticsCompany.data.entity.User;
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
    private Office office;
    private User user;
}
