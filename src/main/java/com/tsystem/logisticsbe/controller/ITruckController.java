package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.dto.TruckDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
public interface ITruckController {

    @PostMapping("/trucks")
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody TruckDTO truckDTO);

    @GetMapping("/trucks")
    public List<TruckDTO> getAll();

    @GetMapping("/trucks/{id}")
    public TruckDTO getById(@PathVariable("id") Long id);

    @PutMapping("/trucks/{id}")
    public Long update(@PathVariable("id") Long id, @RequestBody TruckDTO truckDTO);

    @DeleteMapping("/trucks/{id}")
    public Long delete(@PathVariable("id") Long id);
}
