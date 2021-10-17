package com.tsystem.logisticsbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsystem.logisticsbe.entity.domain.TransportOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransportOrderDTO {

    @Builder.Default
    private final String number = "ORD-".concat(
            UUID.randomUUID()
                    .toString()
                    .substring(0, 5)
                    .replaceAll("-", ""));
    private Long id;
    private TransportOrderStatus status = TransportOrderStatus.IN_PROGRESS;
    @JsonProperty("way_points")
    private List<WayPointDTO> wayPoints;
    private List<TruckDTO> trucks;
    private List<DriverDTO> drivers;
}
