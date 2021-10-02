package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.CityDTO;
import com.tsystem.logisticsbe.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CityMapper {

    City getCityFromCityDTO(CityDTO cityDTO);

    CityDTO getCityDTOFromCity(City city);

}
