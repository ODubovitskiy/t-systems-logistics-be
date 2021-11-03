package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.entity.Shipment;
import com.tsystem.logisticsbe.repository.ShipmentRepository;
import com.tsystem.logisticsbe.service.api.IShipmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentService implements IShipmentsService {

    private final ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public List<Shipment> getAll() {
        return null;
    }

    @Override
    public List<Shipment> getShipmentsPrepared() {
        return shipmentRepository.getShipmentsPrepared();
    }
}
