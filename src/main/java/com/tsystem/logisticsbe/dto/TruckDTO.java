package com.tsystem.logisticsbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsystem.logisticsbe.entity.domain.TruckStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TruckDTO {

    private String model;
    private Long id;
    @JsonProperty("reg_number")
    private String regNumber;
    @JsonProperty("driver_shift")
    private Integer driverShiftSize;
    @JsonProperty("load_capacity")
    private Integer loadCapacity;
    private TruckStatus status;
    @JsonProperty("city")
    private CityDTO cityDTO;


}
