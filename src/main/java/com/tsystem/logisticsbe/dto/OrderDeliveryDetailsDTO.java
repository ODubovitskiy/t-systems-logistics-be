package com.tsystem.logisticsbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsystem.logisticsbe.entity.City;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDeliveryDetailsDTO {

    private List<DriverDTO> drivers;
    private List<TruckDTO> trucks;
    @JsonProperty("way_points")
    private List<WayPointDTO> wayPoints;

}
