package com.tsystem.logisticsbe.factory;

import com.tsystem.logisticsbe.dto.DriverDTO;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DriverDTOFactory {

    DriverMapper driverMapper;

    @Autowired
    public DriverDTOFactory(DriverMapper driverMapper) {
        this.driverMapper = driverMapper;
    }

    public List<DriverDTO> createDefaultDriverDTOList(List<Driver> entities) {
        return entities.stream()
                .map(driverMapper::mapToDTO)
                .collect(Collectors.toList());
    }

}
