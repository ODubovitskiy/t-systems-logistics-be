package com.tsystem.logisticsbe.factory;

import com.tsystem.logisticsbe.dto.TruckDTO;
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

    private TruckDTO createDefault(Truck entity) {

        return truckMapper.fromTruckEntityToTruckDTO(entity);

//        return TruckDTO.builder()
//                .id(entity.getId())
//                .model(entity.getModel())
//                .regNumber(entity.getRegNumber())
//                .driverShiftSize(entity.getDriverShiftSize())
//                .loadCapacity(entity.getLoadCapacity())
//                .status(entity.getStatus())
//                .cityDTO(cityMapper.getCityDTOFromCity(entity.getCurrentCity()))
//                .build();
    }

    public List<TruckDTO> createDefaultListTruckDTO(List<Truck> entities) {
        return entities
                .stream()
                .map(this::createDefault)
                .collect(Collectors.toList());
    }
}
