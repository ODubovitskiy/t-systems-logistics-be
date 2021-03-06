package com.tsystem.logisticsbe.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface Mapper<E, D> {

    E mapToEntity(D dto);

    D mapToDTO(E entity);

    void updateEntity(E source, E destination);

    default List<D> mapToDtoList(List<E> entities) {
        return entities
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    default List<E> mapToEntityList(List<D> dtos) {
        return dtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    default Set<D> mapToDtoSet(Set<E> entities) {
        return entities
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toSet());
    }

    default Set<E> mapToEntitySet(Set<D> dtos) {
        return dtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toSet());
    }
}
