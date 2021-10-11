package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.entity.Driver;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IDriverService {

    String create(Driver driver);

    List<DriverDTO> getAll();

    DriverDTO getById(Long id);

    Long update(Long id, Driver driver);

    Long delete(Long id);
}
