package com.tsystem.logisticsbe.service.api;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.dto.DriverPersonalAccountDTO;
import com.tsystem.logisticsbe.entity.Driver;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface IDriverService {

    @Transactional
    DriverDTO create(Driver driver);

    List<DriverDTO> getAll();

    DriverDTO getById(Long id);

    @Transactional
    Long update(Long id, Driver driver);

    Long delete(Long id);

    DriverPersonalAccountDTO getDriverPersonalAccount();

   Set<DriverDTO> findDriversByCityId(Long  city);

   Driver getDriverByAppUserId(Long id);
}
