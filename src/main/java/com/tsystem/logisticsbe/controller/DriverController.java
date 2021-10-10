package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.factory.DriverDTOFactory;
import com.tsystem.logisticsbe.mapper.DriverMapper;
import com.tsystem.logisticsbe.service.IDriverService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DriverController implements IDriverController {

    private final IDriverService driverService;
    private final DriverMapper driverMapper;
    private final DriverDTOFactory driverDTOFactory;

    public DriverController(IDriverService driverService, DriverMapper driverMapper, DriverDTOFactory driverDTOFactory) {
        this.driverService = driverService;
        this.driverMapper = driverMapper;
        this.driverDTOFactory = driverDTOFactory;
    }

    @Override
    public String create(@Valid DriverDTO driverDTO) {
        Driver driver = driverMapper.mapToDriver(driverDTO);
        return driverService.create(driver);
    }

    @Override
    public List<DriverDTO> getAll() {
        return driverDTOFactory.createDefaultDriverDTOList(driverService.getAll());
    }

    @Override
    public DriverDTO getById(@PathVariable("id") Long id) {
        driverService.getById(id);
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
