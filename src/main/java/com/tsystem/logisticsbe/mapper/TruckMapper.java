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
    public void updateTruck(Truck source, @MappingTarget Truck destination) {

        destination.setModel(source.getModel());
        destination.setRegNumber(source.getRegNumber());
        destination.setDriverShiftSize(source.getDriverShiftSize());
        destination.setLoadCapacity(source.getLoadCapacity());
        destination.setStatus(source.getStatus());
        destination.setCurrentCity(source.getCurrentCity());
    }


    public Truck mapToEntity(TruckDTO truckDTO) {
        if (truckDTO == null) {
            return null;
        } else {
            Truck truck = new Truck();
            truck.setModel(truckDTO.getModel());
            truck.setId(truckDTO.getId());
            truck.setRegNumber(truckDTO.getRegNumber());
            truck.setDriverShiftSize(truckDTO.getDriverShiftSize());
            truck.setLoadCapacity(truckDTO.getLoadCapacity());
            truck.setStatus(truckDTO.getStatus());
            truck.setCurrentCity(cityMapper.mapToEntity(truckDTO.getCityDTO()));
            return truck;
        }

    }

    ;

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

