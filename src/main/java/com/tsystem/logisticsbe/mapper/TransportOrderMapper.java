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
        if (dto.getTrucks() != null)
            order.setTrucks(truckMapper.mapToEntityList(dto.getTrucks()));
        else if (dto.getDrivers() != null)
            order.setDrivers(driverMapper.mapToEntityList(dto.getDrivers()));
        else if (dto.getWayPoints() != null)
            order.setWayPoints(wayPointMapper.mapToEntityList(dto.getWayPoints()));

        return order;
    }

    @Override
    public TransportOrderDTO mapToDTO(TransportOrder entity) {
        return null;
    }

    @Override
    public void updateEntity(TransportOrder source, TransportOrder destination) {

    }
}
