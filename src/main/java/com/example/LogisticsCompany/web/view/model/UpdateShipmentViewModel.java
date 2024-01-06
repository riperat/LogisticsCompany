package com.example.LogisticsCompany.web.view.model;

import com.example.LogisticsCompany.data.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;


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

    @NotBlank
    private String sender;
}
