package com.tsystem.logisticsbe.factory;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.mapper.TruckMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TruckDTOFactory {

    private final TruckMapper truckMapper;

    @Autowired
    public TruckDTOFactory(TruckMapper truckMapper) {
        this.truckMapper = truckMapper;
    }

    public List<TruckDTO> createDefaultListTruckDTO(List<Truck> entities) {
        return entities
                .stream()
                .map(truckMapper::mapToDTO)
                .collect(Collectors.toList());
    }
}
