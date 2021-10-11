package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.service.IDriverService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DriverController implements IDriverController {

    private final IDriverService driverService;

    public DriverController(IDriverService driverService) {
        this.driverService = driverService;
    }

    @Override
    public String create(DriverDTO driverDTO) {
        return null;
    }

    @Override
    public List<DriverDTO> getAll() {
        return null;
    }

    @Override
    public DriverDTO getById(@PathVariable("id") Long id) {
        return null;
    }

    @Override
    public Long update(@PathVariable("id") Long id, @RequestBody DriverDTO driverDTO) {
        return null;
    }

    @Override
    public Long delete(@PathVariable("id") Long id) {
        return null;
    }
}
