package com.tsystem.logisticsbe.controller.api;

import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.entity.TransportOrder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:8080")
public interface ITransportOrderController {

    @GetMapping("/orders")
    List<TransportOrder> getAll();

    @PostMapping("/orders")
    Long create(TransportOrderDTO orderDTO);
}
