package com.tsystem.logisticsbe.controller.api;

import com.tsystem.logisticsbe.dto.DriverDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:8080")
public interface IDriverController {

    @PostMapping("/drivers")
    String create(@RequestBody DriverDTO driverDTO);

    @GetMapping("/drivers")
    List<DriverDTO> getAll();

    @GetMapping("/drivers/{id}")
    DriverDTO getById(@PathVariable("id") Long id);

    @PutMapping("/drivers/{id}")
    Long update(@PathVariable("id") Long id, @RequestBody DriverDTO driverDTO);

    @DeleteMapping("/drivers/{id}")
    Long delete(@PathVariable("id") Long id);

    @GetMapping("/drivers/lc/{personalNumber}")
    DriverDTO getDriverByPersonalNumber(@PathVariable("personalNumber") String personalNumber);
}
