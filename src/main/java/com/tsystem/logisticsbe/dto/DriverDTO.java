package com.tsystem.logisticsbe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DriverDTO {

    @EqualsAndHashCode.Exclude
    private Long id;

    @JsonIgnore
    public final static int SHIFT_LIMIT = 176;
    private String name;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("personal_number")
    private String personalNumber = UUID
            .randomUUID()
            .toString()
            .substring(0, 6)
            .replaceAll("-", "");

    @JsonProperty("hours_worked")
    private Integer hoursWorked;
    @JsonProperty("status")
    private DriverStatus status;
    @JsonProperty("city")
    private CityDTO cityDTO;
    @JsonProperty("truck")
    private TruckDTO truckDTO;
}
