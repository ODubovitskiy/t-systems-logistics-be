package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.dto.DriverPersonalAccountDTO;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.entity.TransportOrder;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import com.tsystem.logisticsbe.exception.ApiException;
import com.tsystem.logisticsbe.exception.DriverNotFoundExeption;
import com.tsystem.logisticsbe.mapper.DriverMapper;
import com.tsystem.logisticsbe.mapper.TransportOrderMapper;
import com.tsystem.logisticsbe.repository.CityRepository;
import com.tsystem.logisticsbe.repository.DriverRepository;
import com.tsystem.logisticsbe.repository.TruckRepository;
import com.tsystem.logisticsbe.service.api.IDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DriverService implements IDriverService {

    private final DriverRepository driverRepository;
    private final CityRepository cityRepository;
    private final TruckRepository truckRepository;
    private final DriverMapper driverMapper;
    private final TransportOrderMapper transportOrderMapper;

    @Autowired
    public DriverService(DriverRepository driverRepository, CityRepository cityRepository,
                         TruckRepository truckRepository, DriverMapper driverMapper, TransportOrderMapper transportOrderMapper) {
        this.driverRepository = driverRepository;
        this.cityRepository = cityRepository;
        this.truckRepository = truckRepository;
        this.driverMapper = driverMapper;
        this.transportOrderMapper = transportOrderMapper;
    }

    @Override
    @Transactional
    public String create(Driver driver) {
        City city = cityRepository.getById(driver.getCity().getId());
        Truck truck = truckRepository.getById(driver.getTruck().getId());
        driver.setCity(city);
        if (driver.getStatus() != DriverStatus.REST)
            driver.setTruck(truck);
        else
            driver.setTruck(null);
        driverRepository.save(driver);

        return String.format("%s %s", driver.getName(), driver.getLastName());
    }

    @Override
    public List<DriverDTO> getAll() {
        return driverMapper.mapToDtoList(driverRepository.findByIsDeletedNull());
    }

    @Override
    public DriverDTO getById(Long id) {
        Driver driver = driverRepository.findDriverByIdAndIsDeletedNull(id)
                .orElseThrow(() -> {
                    throw new DriverNotFoundExeption(String.format("Driver with id = %s doesn't exist.", id));
                });
        return driverMapper.mapToDTO(driver);
    }

    @Override
    public Long update(Long id, Driver driver) {
        Optional<Driver> optionalDriver = driverRepository.findDriverByIdAndIsDeletedNull(id);
        if (!optionalDriver.isPresent())
            throw new DriverNotFoundExeption(String.format("Driver with id = %s doesn't exist", id));
        Driver driverToUpdate = optionalDriver.get();
        driverMapper.updateEntity(driver, driverToUpdate);
        City city = cityRepository.getById(driver.getCity().getId());
        Truck truck = truckRepository.getById(driver.getTruck().getId());
        driverToUpdate.setCity(city);
        if (driver.getStatus() == DriverStatus.DRIVING | driver.getStatus() == DriverStatus.ON_SHIFT)
            driverToUpdate.setTruck(truck);
        if (driver.getStatus() == DriverStatus.REST)
            driverToUpdate.setTruck(null);

        return driverToUpdate.getId();
    }

    @Override
    public Long delete(Long id) {
        Optional<Driver> optionalDriver = driverRepository.findDriverByIdAndIsDeletedNull(id);
        if (!optionalDriver.isPresent())
            throw new DriverNotFoundExeption(String.format("Driver with id = %s doesn't exist", id));
        Driver driver = optionalDriver.get();
        driver.setIsDeleted(LocalDateTime.now());
        driverRepository.saveAndFlush(driver);
        return id;
    }

    @Override
    public DriverPersonalAccountDTO getDriverByPersonalNumber(String number) {
        DriverPersonalAccountDTO driverPersonalAccountDTO = new DriverPersonalAccountDTO();

        // TODO: 03.11.2021 Replace this request with the current user
        Optional<Driver> driverOptional = driverRepository.getDriverByPersonalNumberAndIsDeletedNull(number);
        if (!driverOptional.isPresent())
            throw new ApiException(HttpStatus.NOT_FOUND, String.format("There is no driver with personal number = '%s'", number));
        Driver driver = driverOptional.get();

        TransportOrder transportOrder = driver.getTransportOrder();
        driverPersonalAccountDTO.setDriver(driverMapper.mapToDTO(driver));

        if (transportOrder == null) {
            return driverPersonalAccountDTO;
        }

        driver.setTransportOrder(new TransportOrder());
        driverPersonalAccountDTO.setTransportOrder(transportOrderMapper.mapToDTO(transportOrder));
        return driverPersonalAccountDTO;
    }

    @Override
    public Set<DriverDTO> findDriversByCityId(Long  cityId) {
        return driverMapper.mapToDtoSet(driverRepository.findDriversByCityId(cityId));
    }
}
