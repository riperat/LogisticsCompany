package com.example.LogisticsCompany.web.dto;

import com.example.LogisticsCompany.data.entity.Client;
import com.example.LogisticsCompany.data.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateShipmentDTO {
    private String name;
    private Long weight;
    private Long price;
    private String deliveryAddress;
    private boolean isReceived;
    private boolean isSend;
    private String recipient;
    private User sender;
    private LocalDateTime shipmentDateTime;
    private LocalDateTime receiveDateTime;
}
