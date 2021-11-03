package com.tsystem.logisticsbe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PreOrderDTO {

    private Set<DriverDTO> drivers;
    private Set<TruckDTO> trucks;

}
