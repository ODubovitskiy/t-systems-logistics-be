package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.entity.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        driver.setCity(cityMapper.mapToEntity(dto.getCity()));
        driver.setTruck(truckMapper.mapToEntity(dto.getTruck()));

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
        driverDTO.setCity(cityMapper.mapToDTO(entity.getCity()));
        driverDTO.setTruck(truckMapper.mapToDTO(entity.getTruck()));

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

    @Override
    public List<DriverDTO> mapToDtoList(List<Driver> entities) {
        return entities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Driver> mapToEntityList(List<DriverDTO> dtos) {
        return dtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
