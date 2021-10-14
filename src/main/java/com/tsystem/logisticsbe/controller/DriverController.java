package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.controller.api.IDriverController;
import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.mapper.DriverMapper;
import com.tsystem.logisticsbe.service.IDriverService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DriverController implements IDriverController {

    private final IDriverService driverService;
    private final DriverMapper driverMapper;

    public DriverController(IDriverService driverService, DriverMapper driverMapper) {
        this.driverService = driverService;
        this.driverMapper = driverMapper;
    }

    @Override
    public String create(DriverDTO driverDTO) {
        Driver driver = driverMapper.mapToEntity(driverDTO);
        return driverService.create(driver);
    }

    @Override
    public List<DriverDTO> getAll() {
        return driverService.getAll();
    }

    @Override
    public DriverDTO getById(@PathVariable("id") Long id) {
        return driverService.getById(id);
    }

    @Override
    public Long update(@PathVariable("id") Long id, @RequestBody DriverDTO driverDTO) {
        Driver driver = driverMapper.mapToEntity(driverDTO);
        return driverService.update(id, driver);
    }

    @Override
    public Long delete(@PathVariable("id") Long id) {
        return driverService.delete(id);
    }
}
