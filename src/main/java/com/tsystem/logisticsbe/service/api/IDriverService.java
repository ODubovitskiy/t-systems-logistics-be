package com.tsystem.logisticsbe.service.api;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.entity.Driver;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IDriverService {

    @Transactional
    String create(Driver driver);

    List<DriverDTO> getAll();

    DriverDTO getById(Long id);

    @Transactional
    Long update(Long id, Driver driver);

    Long delete(Long id);

    List<DriverDTO> getDriversForOrder(Integer travelTime);

    Driver getDriverByAppUSerId(Long id);
}
