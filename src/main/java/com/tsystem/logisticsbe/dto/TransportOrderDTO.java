package com.tsystem.logisticsbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.entity.WayPoint;
import com.tsystem.logisticsbe.entity.domain.TransportOrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
@Builder
@Getter
public class TransportOrderDTO {

    @Builder.Default
    private final String number = "ORD-".concat(
            UUID.randomUUID()
                    .toString()
                    .substring(0, 5)
                    .replaceAll("-", ""));
    private Long id;
    private TransportOrderStatus status;
    @JsonProperty("way_points")
    private List<WayPointDTO> wayPoints;
    private TruckDTO truck;
    private List<DriverDTO> drivers;
}
