package com.tsystem.logisticsbe.entity.domain;

import lombok.Getter;

@Getter
public enum DriverStatus {

    REST(1, "Rest"), DRIVING(2, "Driving"), ON_SHIFT(3, "On shift");

    private final int id;
    private final String status;

    DriverStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }
}
