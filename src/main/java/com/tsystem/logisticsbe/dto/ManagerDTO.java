package com.tsystem.logisticsbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ManagerDTO {

    private String name;

    @JsonProperty("last_name")
    private String lastName;

}
