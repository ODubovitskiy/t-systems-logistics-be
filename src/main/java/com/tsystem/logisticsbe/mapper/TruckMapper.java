package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.Truck;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TruckMapper {

    @Mapping(target = "id", ignore = true)
    void updateFromDTO(TruckDTO truckDTO, @MappingTarget Truck truck);

    Truck mapToEntity(TruckDTO truckDTO);

    TruckDTO mapToDTO(Truck truck);
}

