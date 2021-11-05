package com.tsystem.logisticsbe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsystem.logisticsbe.entity.domain.TruckStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TruckDTO {

    @JsonIgnore
    private Float speedFactor = (float) ThreadLocalRandom
            .current()
            .nextInt(6, 10) / 10;
    private Float averageSpeed = 110 * speedFactor;
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
