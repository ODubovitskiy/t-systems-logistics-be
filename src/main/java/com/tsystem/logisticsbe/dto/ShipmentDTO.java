package com.tsystem.logisticsbe.dto;

import com.tsystem.logisticsbe.entity.domain.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {

    private Long id;
    private ShipmentStatus status;
    private String name;
    private Integer weight;
    private String number;
}
