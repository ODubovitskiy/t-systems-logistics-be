package com.tsystem.logisticsbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {

    private Long id;

//    @Pattern(regexp = "^[A-Za-z]$", message = "Name can consist of letters only")
//    @NotBlank
    private String name;

//    @Pattern(regexp = "^[A-Za-z]$", message = "Last name can consist of letters only")
//    @NotBlank()
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

    private DriverStatus status;
    private CityDTO city;
    private TruckDTO truck;
}
