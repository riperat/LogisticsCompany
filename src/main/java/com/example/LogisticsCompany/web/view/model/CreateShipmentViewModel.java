package com.example.LogisticsCompany.web.view.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateShipmentViewModel {
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

    @NotBlank
    private String sender;
}
