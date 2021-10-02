package com.tsystem.logisticsbe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {

    private Long id;
    private String city;
    private String distance;

}
