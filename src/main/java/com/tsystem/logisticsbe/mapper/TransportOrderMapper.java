package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.entity.TransportOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransportOrderMapper implements Mapper<TransportOrder, TransportOrderDTO> {

    private final TruckMapper truckMapper;
    private final DriverMapper driverMapper;
    private final WayPointMapper wayPointMapper;

    @Autowired
    public TransportOrderMapper(TruckMapper truckMapper, DriverMapper driverMapper, WayPointMapper wayPointMapper) {
        this.truckMapper = truckMapper;
        this.driverMapper = driverMapper;
        this.wayPointMapper = wayPointMapper;
    }

    @Override
    public TransportOrder mapToEntity(TransportOrderDTO dto) {
        if (dto == null) {
            return null;
        }

        TransportOrder order = new TransportOrder();

        order.setId(dto.getId());
        order.setNumber(dto.getNumber());
        order.setStatus(dto.getStatus());
        if (dto.getTruck() != null)
            order.setTruck(truckMapper.mapToEntity(dto.getTruck()));
        if (dto.getDrivers() != null)
            order.setDrivers(driverMapper.mapToEntitySet(dto.getDrivers()));
        if (dto.getWayPoints() != null)
            order.setWayPoints(wayPointMapper.mapToEntityList(dto.getWayPoints()));

        return order;
    }

    @Override
    public TransportOrderDTO mapToDTO(TransportOrder entity) {
        if (entity == null) {
            return null;
        }

        TransportOrderDTO dto = new TransportOrderDTO();
        dto.setId(entity.getId());
        dto.setNumber(entity.getNumber());
        dto.setStatus(entity.getStatus());
        if (entity.getTruck() != null)
            dto.setTruck(truckMapper.mapToDTO(entity.getTruck()));
        if (entity.getDrivers() != null)
            dto.setDrivers(driverMapper.mapToDtoSet(entity.getDrivers()));
        if (entity.getWayPoints() != null)
            dto.setWayPoints(wayPointMapper.mapToDtoList(entity.getWayPoints()));

        return dto;
    }

    @Override
    public void updateEntity(TransportOrder source, TransportOrder destination) {

    }
}
