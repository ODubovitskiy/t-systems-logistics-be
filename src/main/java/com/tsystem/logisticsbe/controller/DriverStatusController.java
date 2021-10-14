package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.controller.api.IDriverStatusController;
import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;

@RestController

public class DriverStatusController implements IDriverStatusController {
    @Override
    public HashMap<Integer, String> getAll() {
        DriverStatus[] statusArr = DriverStatus.values();
        HashMap<Integer, String> statuses = new HashMap<>();
        Arrays.stream(statusArr)
                .forEach(status -> statuses.put(status.getId(), status.getStatus()));
        return statuses;
    }
}
