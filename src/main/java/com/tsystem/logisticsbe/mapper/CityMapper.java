package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.CityDTO;
import com.tsystem.logisticsbe.entity.City;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CityMapper implements Mapper<City, CityDTO> {

    @Override
    public City mapToEntity(CityDTO dto) {
        if (dto == null)
            return null;
        else {
            City city = new City();
            city.setId(dto.getId());
            city.setCity(dto.getCity());
            city.setDistance(dto.getDistance());

            return city;
        }
    }

    @Override
    public CityDTO mapToDTO(City entity) {
        if (entity == null)
            return null;
        else {
            CityDTO dto = new CityDTO();
            dto.setId(entity.getId());
            dto.setCity(entity.getCity());
            dto.setDistance(entity.getDistance());

            return dto;
        }
    }

    @Override
    public void updateEntity(City source, City destination) {
        if (source != null & destination != null) {
            destination.setCity(source.getCity());
            destination.setDistance(source.getDistance());
        }
    }

    @Override
    public List<CityDTO> mapToDtoList(List<City> entities) {
        return entities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<City> mapToEntityList(List<CityDTO> dtos) {
        return null;
    }
}
