package com.example.LogisticsCompany.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client extends BaseEntity {

    @NotBlank
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officeID")
    private Office office;
}
