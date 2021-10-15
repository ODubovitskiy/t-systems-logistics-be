package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.controller.api.ITransportOrderController;
import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.entity.TransportOrder;
import com.tsystem.logisticsbe.service.api.ITransportOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransportOrderController implements ITransportOrderController {

    private final ITransportOrderService ITransportOrderService;

    @Autowired
    public TransportOrderController(ITransportOrderService ITransportOrderService) {
        this.ITransportOrderService = ITransportOrderService;
    }

    @Override
    public List<TransportOrder> getAll() {



        return null;
    }

    @Override
    public Long create(TransportOrderDTO orderDTO) {
        return null;
    }
}
