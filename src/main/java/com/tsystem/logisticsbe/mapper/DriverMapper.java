package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DriverMapper {

    @Autowired
    public CityMapper cityMapper;

    @Autowired
    public TruckMapper truckMapper;

//    @Mapping(target = "personalNumber", ignore = true)
    public abstract Driver mapToDriver(DriverDTO driverDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "personalNumber", ignore = true)
    public abstract void updateDriver(Driver source, @MappingTarget Driver destination);

    public DriverDTO mapToDriverDTO(Driver driver) {
        if (driver == null) {
            return null;
        }

        DriverDTO driverDTO = new DriverDTO();

        driverDTO.setId(driver.getId());
        driverDTO.setName(driver.getName());
        driverDTO.setLastName(driver.getLastName());
        driverDTO.setPersonalNumber(driver.getPersonalNumber());
        driverDTO.setHoursWorked(driver.getHoursWorked());
        driverDTO.setStatus(driver.getStatus());
        driverDTO.setCity(cityMapper.mapToDTO(driver.getCity()));
        driverDTO.setTruck(truckMapper.mapToDTO(driver.getTruck()));

        return driverDTO;
    }

    ;

}
