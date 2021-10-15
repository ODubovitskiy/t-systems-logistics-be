package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.exception.DriverNotFoundExeption;
import com.tsystem.logisticsbe.mapper.DriverMapper;
import com.tsystem.logisticsbe.repository.CityRepository;
import com.tsystem.logisticsbe.repository.DriverRepository;
import com.tsystem.logisticsbe.repository.TruckRepository;
import com.tsystem.logisticsbe.service.api.IDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService implements IDriverService {

    DriverRepository driverRepository;
    CityRepository cityRepository;
    TruckRepository truckRepository;
    DriverMapper driverMapper;

    @Autowired
    public DriverService(DriverRepository driverRepository, CityRepository cityRepository,
                         TruckRepository truckRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.cityRepository = cityRepository;
        this.truckRepository = truckRepository;
        this.driverMapper = driverMapper;
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
    public List<DriverDTO> getAll() {
        return driverMapper.mapToDtoList(driverRepository.findAll());
    }

    @Override
    public DriverDTO getById(Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> {
                    throw new DriverNotFoundExeption(String.format("Driver with id = %s doesn't exist.", id));
                });
        return driverMapper.mapToDTO(driver);
    }

    @Override
    public Long update(Long id, Driver driver) {
        Optional<Driver> optionalDriver = driverRepository.findById(id);
        if (!optionalDriver.isPresent())
            throw new DriverNotFoundExeption(String.format("Driver with id = %s doesn't exist", id));
        Driver driverToUpdate = optionalDriver.get();
        driverMapper.updateEntity(driver, driverToUpdate);
        City city = cityRepository.getById(driver.getCity().getId());
        Truck truck = truckRepository.getById(driver.getTruck().getId());
        driverToUpdate.setCity(city);
        driverToUpdate.setTruck(truck);

        return driverToUpdate.getId();
    }

    @Override
    public Long delete(Long id) {
        Optional<Driver> optionalDriver = driverRepository.findById(id);
        if (!optionalDriver.isPresent())
            throw new DriverNotFoundExeption(String.format("Driver with id = %s doesn't exist", id));
        driverRepository.delete(optionalDriver.get());
        return id;
    }
}
