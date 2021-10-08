package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.dto.DriverDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
public interface IDriverController {

    String GET_ALL = "/drivers";
    String GET_BY_ID = "/drivers/{id}";
    String CREATE = "/drivers";
    String UPDATE = "/drivers/{id}";
    String DELETE = "/drivers/{id}";

    @PostMapping(CREATE)
    String create(@RequestBody DriverDTO driverDTO);

    @GetMapping(GET_ALL)
    List<DriverDTO> getAll();

    @GetMapping(GET_BY_ID)
    DriverDTO getById(@PathVariable("id") Long id);

    @PatchMapping(UPDATE)
    Long update(@PathVariable("id") Long id, @RequestBody DriverDTO driverDTO);

    @DeleteMapping(DELETE)
    Long delete(@PathVariable("id") Long id);


}
