package com.tsystem.logisticsbe.mapper;

import com.tsystem.logisticsbe.dto.WayPointDTO;
import com.tsystem.logisticsbe.entity.WayPoint;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WayPointMapper implements Mapper<WayPoint, WayPointDTO> {
    @Override
    public WayPoint mapToEntity(WayPointDTO dto) {
        return null;
    }

    @Override
    public WayPointDTO mapToDTO(WayPoint entity) {
        return null;
    }

    @Override
    public void updateEntity(WayPoint source, WayPoint destination) {

    }

    @Override
    public List<WayPointDTO> mapToDtoList(List<WayPoint> entities) {
        return entities
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<WayPoint> mapToEntityList(List<WayPointDTO> dtos) {
        return dtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
}
