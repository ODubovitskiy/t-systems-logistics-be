package com.tsystem.logisticsbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsystem.logisticsbe.entity.domain.TransportOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportOrderDTO {

    @JsonProperty("number")
    private String number;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("status")
    private TransportOrderStatus status;

    @JsonProperty("way_points")
    private List<WayPointDTO> wayPoints;

    @JsonProperty("truck")
    private TruckDTO truck;

    @JsonProperty("drivers")
    private Set<DriverDTO> drivers;

    @JsonProperty("travel_time")
    private Integer travelTime;
}
