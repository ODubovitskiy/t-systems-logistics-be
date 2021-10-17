package com.tsystem.logisticsbe.controller.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tsystem.logisticsbe.dto.OrderDeliveryDetailsDTO;
import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:8080")
public interface ITransportOrderController {

    @GetMapping("/orders")
    List<TransportOrderDTO> getAll();

    @PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    Long create(@RequestBody ObjectNode json);

    @GetMapping("/orders/{id}")
    TransportOrderDTO getById(@PathVariable Long id);

    @GetMapping(value = "/orders/order-delivery", consumes = MediaType.APPLICATION_JSON_VALUE)
    OrderDeliveryDetailsDTO getOrderDeliveryData(@RequestBody ObjectNode json);
}
