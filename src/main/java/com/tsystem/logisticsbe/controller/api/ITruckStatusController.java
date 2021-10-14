package com.tsystem.logisticsbe.controller.api;

import com.tsystem.logisticsbe.entity.domain.TruckStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:8080")
public interface ITruckStatusController {

    List<TruckStatus> getAll();

}
