package com.tsystem.logisticsbe.factory;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.mapper.CityMapper;
import com.tsystem.logisticsbe.mapper.TruckMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TruckDTOFactory {

    private final CityMapper cityMapper;
    private final TruckMapper truckMapper;

    @Autowired
    public TruckDTOFactory(CityMapper cityMapper, TruckMapper truckMapper) {
        this.cityMapper = cityMapper;
        this.truckMapper = truckMapper;
    }

    public TruckDTO createDefault(Truck entity) {
        City currentCity = entity.getCurrentCity();
        TruckDTO truckDTO = truckMapper.mapToDTO(entity);
        truckDTO.setCityDTO(cityMapper.mapToDTO(currentCity));
        return truckDTO;
    }

    public List<TruckDTO> createDefaultListTruckDTO(List<Truck> entities) {
        return entities
                .stream()
                .map(this::createDefault)
                .collect(Collectors.toList());
    }
}
