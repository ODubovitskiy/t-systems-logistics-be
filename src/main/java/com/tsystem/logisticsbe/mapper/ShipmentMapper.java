package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.ShipmentDTO;
import com.tsystem.logisticsbe.entity.Shipment;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMapper implements Mapper<Shipment, ShipmentDTO> {
    @Override
    public Shipment mapToEntity(ShipmentDTO dto) {
        if (dto == null) {
            return null;
        }

        Shipment shipment = new Shipment();
        shipment.setId(dto.getId());
        shipment.setNumber(dto.getNumber());
        shipment.setName(dto.getName());
        shipment.setWeight(dto.getWeight());
        shipment.setStatus(dto.getStatus());

        return shipment;
    }

    @Override
    public ShipmentDTO mapToDTO(Shipment entity) {
        if (entity == null) {
            return null;
        }

        ShipmentDTO shipmentDTO = new ShipmentDTO();

        shipmentDTO.setId(entity.getId());
        shipmentDTO.setNumber(entity.getNumber());
        shipmentDTO.setName(entity.getName());
        shipmentDTO.setWeight(entity.getWeight());
        shipmentDTO.setStatus(entity.getStatus());

        return shipmentDTO;
    }

    @Override
    public void updateEntity(Shipment source, Shipment destination) {
        if (source != null & destination != null) {
            destination.setId(source.getId());
            destination.setName(source.getName());
            destination.setNumber(source.getNumber());
            destination.setStatus(source.getStatus());
            destination.setWeight(source.getWeight());
        }
    }
}
