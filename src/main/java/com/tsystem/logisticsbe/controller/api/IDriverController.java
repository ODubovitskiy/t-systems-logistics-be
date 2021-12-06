package com.tsystem.logisticsbe.controller.api;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.dto.DriverPersonalAccountDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:8080")
public interface IDriverController {

    @PostMapping("/drivers")
    @PreAuthorize("hasAnyAuthority('permission:write')")
    DriverDTO create(@RequestBody DriverDTO driverDTO);

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

    @GetMapping("/drivers/personal-account")
    DriverPersonalAccountDTO getDriverPersonalAccount();

    @GetMapping("/drivers/city")
    Set<DriverDTO> findDriversByCityId(@RequestParam(name = "id") Optional<String> id);
}
