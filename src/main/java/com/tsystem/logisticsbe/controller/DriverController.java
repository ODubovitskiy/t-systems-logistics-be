package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.controller.api.IDriverController;
import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.dto.DriverPersonalAccountDTO;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.exception.ApiException;
import com.tsystem.logisticsbe.mapper.DriverMapper;
import com.tsystem.logisticsbe.service.api.IDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class DriverController implements IDriverController {

    private final IDriverService driverService;
    private final DriverMapper driverMapper;

    @Autowired
    public DriverController(IDriverService driverService, DriverMapper driverMapper) {
        this.driverService = driverService;
        this.driverMapper = driverMapper;
    }

    @Override
    public DriverDTO create(DriverDTO driverDTO) {
        Driver driverEntity = driverMapper.mapToEntity(driverDTO);
        return driverService.create(driverEntity);
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

    @Override
    public DriverPersonalAccountDTO getDriverByPersonalNumber(String personalNumber) {
        if (personalNumber.isEmpty())
            throw new ApiException(HttpStatus.BAD_REQUEST, "Number is incorrect");
        return driverService.getDriverByAppUSer();
    }

    @Override
    public Set<DriverDTO> findDriversByCityId(Optional<String> city) {
        Long cityId = Long.valueOf(city.orElseThrow(
                () -> new ApiException(HttpStatus.BAD_REQUEST, "City is incorrect"))
        );
        return driverService.findDriversByCityId(cityId);
    }
}
