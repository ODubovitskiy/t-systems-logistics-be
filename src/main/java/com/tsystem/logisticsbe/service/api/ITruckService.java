package com.tsystem.logisticsbe.service.api;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Truck;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface ITruckService {

    @Transactional
    public Long create(Truck truck);

    public List<TruckDTO> getAll();

    public TruckDTO getByID(Long id);

    @Transactional
    public Long update(Long id, Truck truck);

    @Transactional
    public Long delete(Long id);

    List<TruckDTO> getAvailableTrucks();

    Set<TruckDTO> getTrucksForOrder(List<City> citiesToStart, Integer weight);
}
