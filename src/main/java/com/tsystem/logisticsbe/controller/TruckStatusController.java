package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.entity.domain.TruckStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TruckStatusController implements ITruckStatusController {

    @Override
    @GetMapping("/truck-status")
    public List<TruckStatus> getAll() {
        TruckStatus[] statuses = TruckStatus.values();
        return new ArrayList<>(Arrays.asList(statuses));
    }
}
