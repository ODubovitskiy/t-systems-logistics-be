package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.entity.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper implements Mapper<Driver, DriverDTO> {

    private final CityMapper cityMapper;
    private final TruckMapper truckMapper;

    @Autowired
    public DriverMapper(CityMapper cityMapper, TruckMapper truckMapper) {
        this.cityMapper = cityMapper;
        this.truckMapper = truckMapper;
    }

    @Override
    public Driver mapToEntity(DriverDTO dto) {
        if (dto == null) {
            return null;
        }

        Driver driver = new Driver();

        driver.setId(dto.getId());
        driver.setName(dto.getName());
        driver.setLastName(dto.getLastName());
        driver.setPersonalNumber(dto.getPersonalNumber());
        driver.setHoursWorked(dto.getHoursWorked());
        driver.setStatus(dto.getStatus());
        driver.setCity(cityMapper.mapToEntity(dto.getCityDTO()));
        driver.setTruck(truckMapper.mapToEntity(dto.getTruckDTO()));

        return driver;
    }

    @Override
    public DriverDTO mapToDTO(Driver entity) {
        if (entity == null) {
            return null;
        }

        DriverDTO driverDTO = new DriverDTO();

        driverDTO.setId(entity.getId());
        driverDTO.setName(entity.getName());
        driverDTO.setLastName(entity.getLastName());
        driverDTO.setPersonalNumber(entity.getPersonalNumber());
        driverDTO.setHoursWorked(entity.getHoursWorked());
        driverDTO.setStatus(entity.getStatus());
        driverDTO.setCityDTO(cityMapper.mapToDTO(entity.getCity()));
        driverDTO.setTruckDTO(truckMapper.mapToDTO(entity.getTruck()));

        return driverDTO;
    }

    @Override
    public void updateEntity(Driver source, Driver destination) {
        if (source != null & destination != null) {
            destination.setName(source.getName());
            destination.setLastName(source.getLastName());
            destination.setStatus(source.getStatus());
            destination.setHoursWorked(source.getHoursWorked());
            destination.setCity(source.getCity());
        }
    }
}
