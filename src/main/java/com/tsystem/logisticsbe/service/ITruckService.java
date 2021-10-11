package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.Truck;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ITruckService {

    @Transactional
    public Long create(Truck truck);

    public List<TruckDTO> getAll();

    public TruckDTO getByID(Long id);

    @Transactional
    public Long update(Long id, Truck truck);

    public Long delete(Long id);
}
