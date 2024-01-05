package com.example.LogisticsCompany.web.dto;

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
public class UpdateShipmentDTO {
    private String name;
    private String weight;
    private String price;
    private String deliveryAddress;
    private boolean isDeliveredToOffice;
    private boolean isReceived;
    private boolean isSend;
    private Set<User> recipient;
    private Set<User> sender;
}
