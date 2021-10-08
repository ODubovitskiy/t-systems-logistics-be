package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.dto.TruckDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
public interface ITruckController {

    String GET_ALL = "/trucks";
    String GET_BY_ID = "/trucks/{id}";
    String CREATE = "/trucks";
    String UPDATE = "/trucks/{id}";
    String DELETE = "/trucks/{id}";

    @PostMapping(CREATE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody TruckDTO truckDTO);

    @GetMapping(GET_ALL)
    public List<TruckDTO> getAll();

    @GetMapping(GET_BY_ID)
    public TruckDTO getById(@PathVariable("id") Long id);

    @PutMapping(UPDATE)
    public Long update(@PathVariable("id") Long id, @RequestBody TruckDTO truckDTO);

    @DeleteMapping(DELETE)
    public Long delete(@PathVariable("id") Long id);
}
