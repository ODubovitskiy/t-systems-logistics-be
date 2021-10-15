package com.tsystem.logisticsbe.service.api;

import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.entity.TransportOrder;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ITransportOrderService {

    List<TransportOrder> getAll();

    Long create(TransportOrderDTO orderDTO);
}
