package com.tsystem.logisticsbe.dto;

import com.tsystem.logisticsbe.entity.domain.LoadingType;

public class WayPointDTO {

    private Long id;
    private CityDTO city;
    private ShipmentDTO shipment;
    private LoadingType type;
    private TransportOrderDTO transportOrder;

}
