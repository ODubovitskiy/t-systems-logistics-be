package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.controller.api.IShipmentsStatusController;
import com.tsystem.logisticsbe.entity.domain.ShipmentStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;

@RestController
public class ShipmentStatusController implements IShipmentsStatusController {

    @Override
    public HashMap<Integer, String> getAll() {
        ShipmentStatus[] statusArr = ShipmentStatus.values();
        HashMap<Integer, String> statuses = new HashMap<>();
        Arrays.stream(statusArr)
                .forEach(status -> statuses.put(status.getId(), status.getStatus()));
        return statuses;
    }
}
