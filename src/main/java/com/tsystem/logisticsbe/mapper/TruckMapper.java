package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.Truck;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class TruckMapper {

    @Autowired
    public CityMapper cityMapper;

    @Mapping(target = "id", ignore = true)
    public void updateFromDTO(TruckDTO truckDTO, @MappingTarget Truck truck) {
        if (truckDTO != null) {
            truck.setModel(truckDTO.getModel());
            truck.setRegNumber(truckDTO.getRegNumber());
            truck.setDriverShiftSize(truckDTO.getDriverShiftSize());
            truck.setLoadCapacity(truckDTO.getLoadCapacity());
            truck.setStatus(truckDTO.getStatus());
            truck.setCurrentCity(cityMapper.mapToEntity(truckDTO.getCityDTO()));
        }
    }

    public abstract Truck mapToEntity(TruckDTO truckDTO);

    public TruckDTO mapToDTO(Truck truck) {
        if (truck == null) {
            return null;
        } else {
            TruckDTO truckDTO = new TruckDTO();
            truckDTO.setModel(truck.getModel());
            truckDTO.setId(truck.getId());
            truckDTO.setRegNumber(truck.getRegNumber());
            truckDTO.setDriverShiftSize(truck.getDriverShiftSize());
            truckDTO.setLoadCapacity(truck.getLoadCapacity());
            truckDTO.setStatus(truck.getStatus());
            truckDTO.setCityDTO(cityMapper.mapToDTO(truck.getCurrentCity()));
            return truckDTO;
        }

    }

}

