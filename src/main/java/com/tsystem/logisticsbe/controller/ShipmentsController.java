package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.controller.api.IShipmentController;
import com.tsystem.logisticsbe.dto.ShipmentDTO;
import com.tsystem.logisticsbe.mapper.ShipmentMapper;
import com.tsystem.logisticsbe.service.api.IShipmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShipmentsController implements IShipmentController {

    private final IShipmentsService shipmentsService;
    private final ShipmentMapper shipmentMapper;

    @Autowired
    public ShipmentsController(IShipmentsService shipmentsService, ShipmentMapper shipmentMapper) {
        this.shipmentsService = shipmentsService;
        this.shipmentMapper = shipmentMapper;
    }

    @Override
    public List<ShipmentDTO> getAll() {
        return null;
    }

    @Override
    public List<ShipmentDTO> getShipmentsPrepared() {
        return shipmentMapper.mapToDtoList(shipmentsService.getShipmentsPrepared());
    }
}
