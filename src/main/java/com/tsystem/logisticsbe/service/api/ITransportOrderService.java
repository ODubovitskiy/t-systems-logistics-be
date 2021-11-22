package com.tsystem.logisticsbe.service.api;

import com.tsystem.logisticsbe.dto.PreOrderDTO;
import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.entity.TransportOrder;
import com.tsystem.logisticsbe.entity.WayPoint;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ITransportOrderService {

    List<TransportOrderDTO> getAll();

    @Transactional(rollbackFor = Exception.class)
    TransportOrder create(TransportOrder order);

    PreOrderDTO getPreOrder(PreOrderDTO preOrderDTO, List<WayPoint> wayPoints);

    TransportOrderDTO getById(Long id);
}
