package com.tsystem.logisticsbe.entity.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Locale;

@Getter
public enum ShipmentStatus {

    @JsonProperty("PREPARED")
    PREPARED(1, "PREPARED"),
    @JsonProperty("IN PROGRESS")
    IN_PROGRESS(2, "IN PROGRESS"),
    @JsonProperty("DELIVERED")
    DELIVERED(3, "DELIVERED");

    private final Integer id;
    private final String status;

    ShipmentStatus(Integer id, String status) {
        this.id = id;
        this.status = status.toUpperCase(Locale.ROOT);
    }
}
