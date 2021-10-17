package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.entity.TransportOrder;
import com.tsystem.logisticsbe.entity.WayPoint;
import com.tsystem.logisticsbe.mapper.TransportOrderMapper;
import com.tsystem.logisticsbe.repository.TransportOrderRepository;
import com.tsystem.logisticsbe.repository.TruckRepository;
import com.tsystem.logisticsbe.service.api.ITransportOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportOrderService implements ITransportOrderService {

    private final TransportOrderRepository transportOrderRepository;
    private final TransportOrderMapper transportOrderMapper;
    private final TruckRepository truckRepository;

    @Autowired
    public TransportOrderService(TransportOrderRepository transportOrderRepository, TransportOrderMapper transportOrderMapper
            , TruckRepository truckRepository) {
        this.transportOrderRepository = transportOrderRepository;
        this.transportOrderMapper = transportOrderMapper;
        this.truckRepository = truckRepository;

    }


    @Override
    public List<TransportOrderDTO> getAll() {
        return transportOrderMapper.mapToDtoList(transportOrderRepository.findAll());
    }

    @Override
    public TransportOrder create(TransportOrder order, List<WayPoint> route) {
        //Todo Transactional method does a lot of things.






        //Save Order only after all drivers and trucks are checked
        TransportOrder savedOrder = transportOrderRepository.save(order);
        route.forEach(item -> {
            item.setOrder(savedOrder);
        });

        return savedOrder;
    }
}
