package com.tsystem.logisticsbe.service.api;

import com.tsystem.logisticsbe.dto.ShipmentDTO;
import com.tsystem.logisticsbe.entity.Shipment;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface IShipmentsService {

    List<Shipment> getAll();

    List<Shipment> getShipmentsPrepared();

}
