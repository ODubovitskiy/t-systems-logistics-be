package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.CityDTO;
import com.tsystem.logisticsbe.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    City mapToEntity(CityDTO cityDTO);

    CityDTO mapToDTO(City city);

}
