package com.tsystem.logisticsbe.mapper;

import java.util.List;

public interface Mapper<E, D> {

    E mapToEntity(D dto);

    D mapToDTO(E entity);

    void updateEntity(E source, E destination);

    List<D> mapToDtoList(List<E> entities);

    List<E> mapToEntityList(List<D> dtos);
}
