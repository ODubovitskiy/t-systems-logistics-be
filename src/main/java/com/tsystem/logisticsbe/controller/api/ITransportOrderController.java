package com.tsystem.logisticsbe.controller.api;

import com.tsystem.logisticsbe.dto.PreOrderDTO;
import com.tsystem.logisticsbe.dto.TransportOrderDTO;
import com.tsystem.logisticsbe.entity.TransportOrder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:8080")
public interface ITransportOrderController {

    @GetMapping("/orders")
    List<TransportOrderDTO> getAll();

    @PostMapping(value = "/orders")
    TransportOrder create(@RequestBody TransportOrderDTO transportOrderDTO);

    @GetMapping("/orders/{id}")
    TransportOrderDTO getById(@PathVariable Long id);

    @PostMapping(value = "/orders/preorder")
    PreOrderDTO getPreOrder(@RequestBody TransportOrderDTO transportOrderDTO);

    @PutMapping("/orders/{id}")
    TransportOrderDTO update(@PathVariable("id") Long id, @RequestBody TransportOrderDTO transportOrderDTO);

//    @GetMapping( "/order-drivers-verification")
//    void checkOrderOrderDriversIds(@RequestBody String id, @RequestBody String personal_number):
}
