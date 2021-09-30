package com.tsystem.logisticsbe.dto;

import com.tsystem.logisticsbe.entity.CityEntity;
import com.tsystem.logisticsbe.entity.TruckStatusEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TruckDTO {

    private String model;
    private Long id;
    private String regNumber;
    private Integer driverShiftSize;
    private Integer loadCapacity;
    private TruckStatusEntity truckStatus;
    private CityEntity currentCity;


}
