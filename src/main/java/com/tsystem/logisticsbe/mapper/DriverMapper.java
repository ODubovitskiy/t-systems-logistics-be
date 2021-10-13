package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.entity.Driver;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DriverMapper {

    @Autowired
    public  CityMapper cityMapper;

    @Autowired
    public  TruckMapper truckMapper;

    public abstract Driver mapToDriver(DriverDTO driverDTO);

    public DriverDTO mapToDriverDTO(Driver driver) {
        if (driver == null) {
            return null;
        }

        DriverDTO driverDTO = new DriverDTO();

        driverDTO.setId(driver.getId());
        driverDTO.setName(driver.getName());
        driverDTO.setLastName(driver.getLastName());
        driverDTO.setPersonamNumber(driver.getPersonamNumber());
        driverDTO.setHoursWorked(driver.getHoursWorked());
        driverDTO.setStatus(driver.getStatus());
        driverDTO.setCity(cityMapper.mapToDTO(driver.getCity()));
        driverDTO.setTruck(truckMapper.mapToDTO(driver.getTruck()));

        return driverDTO;
    }

    ;

}
