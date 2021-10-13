package com.tsystem.logisticsbe.factory;

import com.tsystem.logisticsbe.dto.CityDTO;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CityFactory {

    private final CityMapper cityMapper;

    @Autowired
    public CityFactory(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    public List<CityDTO> createDefaultCityDTOList(List<City> cities) {
        return cities.stream()
                .map(cityMapper::mapToDTO)
                .collect(Collectors.toList());
    }
}
