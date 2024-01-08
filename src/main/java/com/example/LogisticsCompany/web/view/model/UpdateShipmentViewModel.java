package com.example.LogisticsCompany.web.view.model;

import com.example.LogisticsCompany.data.entity.Office;
import com.example.LogisticsCompany.data.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateShipmentViewModel {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    private Long weight;

    private Long price;

    @NotBlank
    private String deliveryAddress;

    private boolean isReceived;

    private boolean isSend;

    @NotBlank
    private String recipient;

    private User sender;

    private Office office;

    private LocalDateTime shipmentDateTime;

    private LocalDateTime receiveDateTime;

}
