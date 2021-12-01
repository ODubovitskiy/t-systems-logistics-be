package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.controller.api.ITransportOrderController;
import com.tsystem.logisticsbe.dto.PreOrderDTO;
import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.entity.TransportOrder;
import com.tsystem.logisticsbe.entity.WayPoint;
import com.tsystem.logisticsbe.mapper.DriverMapper;
import com.tsystem.logisticsbe.mapper.TransportOrderMapper;
import com.tsystem.logisticsbe.mapper.WayPointMapper;
import com.tsystem.logisticsbe.service.DriverService;
import com.tsystem.logisticsbe.service.api.ITransportOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransportOrderController implements ITransportOrderController {

    private final ITransportOrderService transportOrderService;
    private final WayPointMapper wayPointMapper;
    private final TransportOrderMapper transportOrderMapper;
    private final DriverMapper driverMapper;
    private final DriverService driverService;

    @Autowired
    public TransportOrderController(ITransportOrderService transportOrderService, WayPointMapper wayPointMapper,
                                    TransportOrderMapper transportOrderMapper, DriverMapper driverMapper, DriverService driverService) {
        this.transportOrderService = transportOrderService;
        this.wayPointMapper = wayPointMapper;
        this.transportOrderMapper = transportOrderMapper;
        this.driverMapper = driverMapper;
        this.driverService = driverService;
    }

    @Override
    public List<TransportOrderDTO> getAll() {
        return transportOrderService.getAll();
    }

    @Override
    public TransportOrder create(TransportOrderDTO transportOrderDTO) {
        TransportOrder order = transportOrderMapper.mapToEntity(transportOrderDTO);
        return transportOrderService.create(order);
    }

    @Override
    public TransportOrderDTO getById(Long id) {
        return transportOrderService.getById(id);
    }

    public PreOrderDTO getPreOrder(TransportOrderDTO transportOrderDTO) {
        List<WayPoint> wayPoints = wayPointMapper.mapToEntityList(transportOrderDTO.getWayPoints());
        PreOrderDTO preOrderDTO = new PreOrderDTO();
        return transportOrderService.getPreOrder(preOrderDTO, wayPoints);
    }

    @Override
    public TransportOrderDTO update(Long id, TransportOrderDTO transportOrderDTO) {
        TransportOrder transportOrder = transportOrderMapper.mapToEntity(transportOrderDTO);
        return  transportOrderService.update(transportOrder, id);
    }
}
