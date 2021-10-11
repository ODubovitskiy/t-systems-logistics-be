package com.tsystem.logisticsbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.TransportOrder;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {

    private Long id;

    @Pattern(regexp = "^[A-Za-z]$")
    @NotBlank
    private String name;

    @Pattern(regexp = "^[A-Za-z]$")
    @NotBlank
    @JsonProperty(namespace = "last_name")
    private String lastName;

    @NotBlank
    @Builder.Default
    @JsonProperty(namespace = "personal_number")
    private String personamNumber = UUID
            .randomUUID()
            .toString()
            .substring(0, 6)
            .replaceAll("-", "");

    @JsonProperty(namespace = "hours_worked")
    private Integer hoursWorked;

    private DriverStatus status;
    private City city;
    private Truck truck;

    @JsonProperty(namespace = "transport_orders")
    private List<TransportOrder> transportOrders;
}
