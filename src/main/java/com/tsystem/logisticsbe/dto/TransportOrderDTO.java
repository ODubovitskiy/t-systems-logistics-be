package com.tsystem.logisticsbe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsystem.logisticsbe.entity.domain.TransportOrderStatus;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportOrderDTO {

    private String number = "ORD-".concat(
            UUID.randomUUID()
                    .toString()
                    .substring(0, 5)
                    .replaceAll("-", ""));
    private Long id;
    private TransportOrderStatus status = TransportOrderStatus.IN_PROGRESS;

    @JsonProperty("way_points")
    private List<WayPointDTO> wayPoints;
    private TruckDTO truck;
    private Set<DriverDTO> drivers;

    @JsonProperty("travel_time")
    private Integer travelTime;
}
