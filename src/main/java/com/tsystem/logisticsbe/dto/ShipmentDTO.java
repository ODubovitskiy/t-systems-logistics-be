package com.tsystem.logisticsbe.dto;

import com.tsystem.logisticsbe.entity.domain.ShipmentStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {

    private Long id;
    private ShipmentStatus status;
    private String name;
    private Integer weight;

}
