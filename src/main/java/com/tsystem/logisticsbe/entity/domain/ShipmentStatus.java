package com.tsystem.logisticsbe.entity.domain;

import lombok.Getter;

@Getter
public enum ShipmentStatus {

    PREPARED(1, "Prepared"), IN_PROGRESS(2, "In progress"), DELIVERED(3, "Delivered");

    private final Integer id;
    private final String status;

    ShipmentStatus(Integer id, String status) {
        this.id = id;
        this.status = status;
    }
}
