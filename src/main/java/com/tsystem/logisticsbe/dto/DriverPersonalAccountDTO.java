package com.tsystem.logisticsbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverPersonalAccountDTO {

    private DriverDTO driver;
    @JsonProperty("transport_order")
    private TransportOrderDTO transportOrder;
}
