package com.tsystem.logisticsbe.mapper;

public interface Mapper<E, D> {

    E mapToEntity(D dto);

    D mapToDTO(E entity);

    void updateEntity(E source, E destination);
}
