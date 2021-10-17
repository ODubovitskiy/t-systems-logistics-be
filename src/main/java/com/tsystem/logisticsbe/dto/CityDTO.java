package com.tsystem.logisticsbe.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CityDTO {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String city;
    private Integer distance;

}
