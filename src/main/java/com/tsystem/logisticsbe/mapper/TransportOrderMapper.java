package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.entity.TransportOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransportOrderMapper implements Mapper<TransportOrder, TransportOrderDTO> {

    private final CityMapper cityMapper;
    private final TruckMapper truckMapper;
    private final DriverMapper driverMapper;
    private final WayPointMapper wayPointMapper;

    @Autowired
    public TransportOrderMapper(CityMapper cityMapper, TruckMapper truckMapper, DriverMapper driverMapper, WayPointMapper wayPointMapper) {
        this.cityMapper = cityMapper;
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
        order.setTruck(truckMapper.mapToEntity(dto.getTruck()));
        order.setDrivers(driverMapper.mapToEntityList(dto.getDrivers()));
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

    @Override
    public List<TransportOrderDTO> mapToDtoList(List<TransportOrder> entities) {
        return null;
    }

    @Override
    public List<TransportOrder> mapToEntityList(List<TransportOrderDTO> dtos) {
        return null;
    }
}
