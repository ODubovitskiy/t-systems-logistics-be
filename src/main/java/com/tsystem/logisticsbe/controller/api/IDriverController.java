package com.tsystem.logisticsbe.controller.api;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.dto.DriverPersonalAccountDTO;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Driver;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @GetMapping("/drivers/personal-account/{personalNumber}")
    DriverPersonalAccountDTO getDriverByPersonalNumber(@PathVariable("personalNumber") String personalNumber);

    @GetMapping("/drivers/city")
    Set<DriverDTO> findDriversByCityId(@RequestParam(name = "id") Optional<String> id);
}
