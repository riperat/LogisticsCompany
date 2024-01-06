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
public class CreateShipmentDTO {
    private String name;
    private Long weight;
    private Long price;
    private String deliveryAddress;
    private boolean isReceived;
    private boolean isSend;
    private String recipient;
    private String sender;
}
