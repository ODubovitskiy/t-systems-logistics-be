package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.dto.DriverPersonalAccountDTO;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.entity.TransportOrder;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import com.tsystem.logisticsbe.exception.ApiException;
import com.tsystem.logisticsbe.kafka.CustomKafkaProducer;
import com.tsystem.logisticsbe.mapper.DriverMapper;
import com.tsystem.logisticsbe.mapper.TransportOrderMapper;
import com.tsystem.logisticsbe.repository.AppUserRepository;
import com.tsystem.logisticsbe.repository.CityRepository;
import com.tsystem.logisticsbe.repository.DriverRepository;
import com.tsystem.logisticsbe.repository.TruckRepository;
import com.tsystem.logisticsbe.security.AppUser;
import com.tsystem.logisticsbe.service.api.IDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AppUserRepository appUserRepository;
    private final CustomKafkaProducer customKafkaProducer;


    @Autowired
    public DriverService(DriverRepository driverRepository, CityRepository cityRepository,
                         TruckRepository truckRepository, DriverMapper driverMapper, TransportOrderMapper transportOrderMapper, AppUserRepository appUserRepository, CustomKafkaProducer customKafkaProducer) {
        this.driverRepository = driverRepository;
        this.cityRepository = cityRepository;
        this.truckRepository = truckRepository;
        this.driverMapper = driverMapper;
        this.transportOrderMapper = transportOrderMapper;
        this.appUserRepository = appUserRepository;
        this.customKafkaProducer = customKafkaProducer;
    }

    @Override
    @Transactional
    public DriverDTO create(Driver driver) {
        City city = cityRepository.getById(driver.getCity().getId());
        driver.setCity(city);
        driver.setTruck(null);
        DriverDTO driverDTO = driverMapper.mapToDTO(driverRepository.save(driver));

        return driverDTO;
    }

    @Override
    public List<DriverDTO> getAll() {
        List<DriverDTO> driverDTOList = driverMapper.mapToDtoList(driverRepository.findByIsDeletedNull());

        return driverDTOList;
    }

    @Override
    public DriverDTO getById(Long id) {
        Driver driver = driverRepository.findDriverByIdAndIsDeletedNull(id)
                .orElseThrow(() -> {
                    throw new ApiException(HttpStatus.NOT_FOUND, String.format("Driver with id = %s doesn't exist.", id));
                });
        return driverMapper.mapToDTO(driver);
    }

    @Override
    public Long update(Long id, Driver driver) {
        Optional<Driver> optionalDriver = driverRepository.findDriverByIdAndIsDeletedNull(id);
        if (!optionalDriver.isPresent())
            throw new ApiException(HttpStatus.NOT_FOUND, String.format("Driver with id = %s doesn't exist", id));
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
            throw new ApiException(HttpStatus.NOT_FOUND, String.format("Driver with id = %s doesn't exist", id));
        Driver driver = optionalDriver.get();
        driver.setIsDeleted(LocalDateTime.now());
        driverRepository.saveAndFlush(driver);

        return id;
    }

    @Override
    public DriverPersonalAccountDTO getDriverByPersonalNumber(String number) {
        DriverPersonalAccountDTO driverPersonalAccountDTO = new DriverPersonalAccountDTO();

        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails principal = (UserDetails) securityContext.getAuthentication().getPrincipal();

        AppUser appUser = appUserRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> {
                    throw new ApiException(HttpStatus.NOT_FOUND, String.format("User with email '%s' doesn't exist", principal.getUsername()));
                });
        Driver driver = driverRepository.getDriverByAppUserId(appUser.getId())
                .orElseThrow(() -> {
                    throw new ApiException(HttpStatus.NOT_FOUND, String.format("Driver with email '%s' doesn't exist", principal.getUsername()));
                });

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
    public Set<DriverDTO> findDriversByCityId(Long cityId) {
        return driverMapper.mapToDtoSet(driverRepository.findDriversByCityId(cityId));
    }

    @Override
    public Driver getDriverByAppUSerId(Long id) {
        return driverRepository.getDriverByAppUserId(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Driver not found"));
    }
}
