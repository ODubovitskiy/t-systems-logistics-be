package com.tsystem.logisticsbe.entity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Locale;

@Getter
public enum DriverStatus {

    @JsonProperty("REST")
    REST(1, "REST"),
    @JsonProperty("DRIVING")
    DRIVING(2, "DRIVING"),
    @JsonProperty("ON SHIFT")
    ON_SHIFT(3, "ON SHIFT");

    private final int id;
    private final String status;

    DriverStatus(int id, String status) {
        this.id = id;
        this.status = status.toUpperCase(Locale.ROOT);
    }
}
