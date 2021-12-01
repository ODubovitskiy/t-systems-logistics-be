package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.WayPointDTO;
import com.tsystem.logisticsbe.entity.WayPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class WayPointMapper implements Mapper<WayPoint, WayPointDTO> {

    private final CityMapper cityMapper;
    private final ShipmentMapper shipmentMapper;
    private final TransportOrderMapper transportOrderMapper;

    @Autowired
    public WayPointMapper(CityMapper cityMapper, ShipmentMapper shipmentMapper,
                          @Lazy TransportOrderMapper transportOrderMapper) {
        this.cityMapper = cityMapper;
        this.shipmentMapper = shipmentMapper;
        this.transportOrderMapper = transportOrderMapper;
    }

    @Override
    public WayPoint mapToEntity(WayPointDTO dto) {
        if (dto == null) {
            return null;
        }

        WayPoint wayPoint = new WayPoint();

        wayPoint.setId(dto.getId());
        wayPoint.setType(dto.getType());
        wayPoint.setCity(cityMapper.mapToEntity(dto.getCity()));
        wayPoint.setShipment(shipmentMapper.mapToEntity(dto.getShipment()));
        wayPoint.setOrder(transportOrderMapper.mapToEntity(dto.getTransportOrder()));

        return wayPoint;
    }

    @Override
    public WayPointDTO mapToDTO(WayPoint entity) {
        if (entity == null) {
            return null;
        }

        WayPointDTO dto = new WayPointDTO();

        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setCity(cityMapper.mapToDTO(entity.getCity()));
        dto.setShipment(shipmentMapper.mapToDTO(entity.getShipment()));

        return dto;
    }

    @Override
    public void updateEntity(WayPoint source, WayPoint destination) {
        if (source != null & destination != null) {
            destination.setId(source.getId());
            destination.setShipment(source.getShipment());
            destination.setCity(source.getCity());
            destination.setOrder(source.getOrder());
            destination.setType(source.getType());
        }
    }
}
