package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.repository.CityRepository;
import com.tsystem.logisticsbe.repository.DriverRepository;
import com.tsystem.logisticsbe.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DriverService implements IDriverService {

    DriverRepository driverRepository;
    CityRepository cityRepository;
    TruckRepository truckRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository, CityRepository cityRepository, TruckRepository truckRepository) {
        this.driverRepository = driverRepository;
        this.cityRepository = cityRepository;
        this.truckRepository = truckRepository;
    }

    @Override
    @Transactional
    public String create(Driver driver) {
        City city = cityRepository.getById(driver.getCity().getId());
        Truck truck = truckRepository.getById(driver.getTruck().getId());
        driver.setCity(city);
        driver.setTruck(truck);
        driverRepository.save(driver);

        return String.format("%s %s", driver.getName(), driver.getLastName());
    }

    @Override
    public List<Driver> getAll() {
        return driverRepository.findAll();
    }

    @Override
    public Driver getById(Long id) {
        return driverRepository.getById(id);
    }

    @Override
    public Long update(Long id, Driver driver) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }
}
