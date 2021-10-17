package com.tsystem.logisticsbe.controller.api;

import com.tsystem.logisticsbe.dto.DriverDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:8080")
public interface IDriverController {

    @PostMapping("/drivers")
    @PreAuthorize("hasAnyAuthority('permission:write')")
    String create(@RequestBody DriverDTO driverDTO);

    @GetMapping("/drivers")
    @PreAuthorize("hasAnyAuthority('permission:read')")
    List<DriverDTO> getAll();

    @GetMapping("/drivers/{id}")
    @PreAuthorize("hasAnyAuthority('permission:read')")
    DriverDTO getById(@PathVariable("id") Long id);

    @PutMapping("/drivers/{id}")
    @PreAuthorize("hasAnyAuthority('permission:write')")
    Long update(@PathVariable("id") Long id, @RequestBody DriverDTO driverDTO);

    @DeleteMapping("/drivers/{id}")
    @PreAuthorize("hasAnyAuthority('permission:write')")
    Long delete(@PathVariable("id") Long id);
}
