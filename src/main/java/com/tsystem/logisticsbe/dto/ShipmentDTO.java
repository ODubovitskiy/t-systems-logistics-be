package com.tsystem.logisticsbe.dto;

import com.tsystem.logisticsbe.entity.domain.ShipmentStatus;

public class ShipmentDTO {

    private Long id;
    private ShipmentStatus status;
    private String number;
    private String name;
    private Integer weight;

}
