package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.TruckEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TruckMapper {

    void updateTruckFromTruckDTO(TruckDTO truckDTO, @MappingTarget TruckEntity truckEntity);

    TruckEntity fromTruckDTOToTruckEntity(TruckDTO truckDTO);

    TruckDTO fromTruckEntityToTruckDTO(TruckEntity truckEntity);
}

