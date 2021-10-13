package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.entity.Driver;

import java.util.List;

public interface IDriverService {

    String create(Driver driver);

    List<Driver> getAll();

    Driver getById(Long id);

    Long update(Long id, Driver driver);

    Long delete(Long id);
}
