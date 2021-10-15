package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.entity.TransportOrder;
import com.tsystem.logisticsbe.service.api.ITransportOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportOrderService implements ITransportOrderService {
    @Override
    public List<TransportOrder> getAll() {
        return null;
    }

    @Override
    public Long create(TransportOrderDTO orderDTO) {
        return null;
    }
}
