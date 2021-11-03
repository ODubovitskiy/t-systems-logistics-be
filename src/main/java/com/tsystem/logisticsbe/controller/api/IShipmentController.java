package com.tsystem.logisticsbe.controller.api;

import com.tsystem.logisticsbe.dto.ShipmentDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:8080")
public interface IShipmentController {

    @GetMapping("/shipments")
    List<ShipmentDTO> getAll();

    @GetMapping("/shipments-prepared")
    List<ShipmentDTO> getShipmentsPrepared();

}
