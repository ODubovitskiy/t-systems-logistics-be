package com.tsystem.logisticsbe.controller.api;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.Truck;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:8080")
public interface ITruckController {

    @PostMapping("/trucks")
    @ResponseStatus(HttpStatus.CREATED)
    TruckDTO create(@RequestBody TruckDTO truckDTO);

    @GetMapping("/trucks")
    List<TruckDTO> getAll();

    @GetMapping("/trucks/{id}")
    TruckDTO getById(@PathVariable("id") Long id);

    @PutMapping("/trucks/{id}")
    Long update(@PathVariable("id") Long id, @RequestBody TruckDTO truckDTO);

    @DeleteMapping("/trucks/{id}")
    Long delete(@PathVariable("id") Long id);

    @GetMapping("/trucks/available")
    List<TruckDTO> getAvailableTrucks();
}
