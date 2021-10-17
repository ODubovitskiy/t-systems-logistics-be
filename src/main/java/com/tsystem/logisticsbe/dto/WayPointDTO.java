package com.tsystem.logisticsbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsystem.logisticsbe.entity.domain.LoadingType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WayPointDTO {

    private Long id;
    private CityDTO city;
    private ShipmentDTO shipment;
    private LoadingType type;
    @JsonProperty("transport_order")
    private TransportOrderDTO transportOrder;

}
