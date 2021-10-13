package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.exception.TruckNotFoundException;
import com.tsystem.logisticsbe.factory.TruckDTOFactory;
import com.tsystem.logisticsbe.mapper.TruckMapper;
import com.tsystem.logisticsbe.repository.CityRepository;
import com.tsystem.logisticsbe.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TruckService implements ITruckService {

    private final TruckRepository truckRepository;
    private final CityRepository cityRepository;
    private final TruckDTOFactory truckDTOFactory;
    private final TruckMapper truckMapper;


    @Autowired
    public TruckService(TruckRepository truckRepository, CityRepository cityRepository, TruckDTOFactory truckDTOFactory,
                        TruckMapper truckMapper) {
        this.truckRepository = truckRepository;
        this.cityRepository = cityRepository;
        this.truckDTOFactory = truckDTOFactory;
        this.truckMapper = truckMapper;
    }

    public Long create(Truck truck) {
        City city = cityRepository.getById(truck.getCurrentCity().getId());
        truck.setCurrentCity(city);

        return truckRepository.saveAndFlush(truck).getId();
    }

    public List<TruckDTO> getAll() {
        List<Truck> truckEntities = truckRepository.findByIsDeletedNull();
        return truckDTOFactory.createDefaultListTruckDTO(truckEntities);
    }

    public TruckDTO getByID(Long id) {
        Truck entity = truckRepository.findById(id)
                .orElseThrow(() -> new TruckNotFoundException(String.format("Truck with id %s doesn't exist", id)));
        return truckMapper.mapToDTO(entity);
    }

    public Long update(Long id, Truck truck) {

        Optional<Truck> truckOptional = truckRepository.findById(id);
        if (!truckOptional.isPresent()) {
            throw new TruckNotFoundException(String.format("Truck with id = %s doesn't exist", id));
        }
        Truck entityToUpdate = truckOptional.get();
        City city = cityRepository.getById(truck.getCurrentCity().getId());
        truckMapper.updateTruck(truck, entityToUpdate);
        entityToUpdate.setCurrentCity(city);
        truckRepository.saveAndFlush(entityToUpdate);

        return entityToUpdate.getId();
    }

    public Long delete(Long id) {

        Truck truck = truckRepository.findById(id)
                .orElseThrow(() -> new TruckNotFoundException(String.format("Truck with id = %s doesn't exist", id)));
        truck.setIsDeleted(LocalDateTime.now());

        return truck.getId();
    }
}
