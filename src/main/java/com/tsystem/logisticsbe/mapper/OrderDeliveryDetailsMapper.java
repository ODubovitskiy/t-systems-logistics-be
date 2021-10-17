package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.OrderDeliveryDetailsDTO;
import com.tsystem.logisticsbe.service.helper.OrderDeliveryDetails;
import org.springframework.stereotype.Component;

@Component
public class OrderDeliveryDetailsMapper implements Mapper<OrderDeliveryDetails, OrderDeliveryDetailsDTO> {
    @Override
    public OrderDeliveryDetails mapToEntity(OrderDeliveryDetailsDTO dto) {
        if (dto == null) {
            return null;
        }

        OrderDeliveryDetails order = new OrderDeliveryDetails();
        order.setDrivers(dto.getDrivers());
        order.setTrucks(dto.getTrucks());
        order.setWayPoints(dto.getWayPoints());

        return order;
    }

    @Override
    public OrderDeliveryDetailsDTO mapToDTO(OrderDeliveryDetails entity) {
        if (entity == null) {
            return null;
        }

        OrderDeliveryDetailsDTO orderDTO = new OrderDeliveryDetailsDTO();
        orderDTO.setDrivers(entity.getDrivers());
        orderDTO.setTrucks(entity.getTrucks());
        orderDTO.setWayPoints(entity.getWayPoints());

        return orderDTO;
    }

    @Override
    public void updateEntity(OrderDeliveryDetails source, OrderDeliveryDetails destination) {

    }
}
