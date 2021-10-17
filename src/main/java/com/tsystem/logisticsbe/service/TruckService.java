package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.exception.ApiException;
import com.tsystem.logisticsbe.exception.TruckNotFoundException;
import com.tsystem.logisticsbe.mapper.TruckMapper;
import com.tsystem.logisticsbe.repository.CityRepository;
import com.tsystem.logisticsbe.repository.DriverRepository;
import com.tsystem.logisticsbe.repository.TruckRepository;
import com.tsystem.logisticsbe.service.api.ITruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TruckService implements ITruckService {

    private final TruckRepository truckRepository;
    private final CityRepository cityRepository;
    private final TruckMapper truckMapper;
    private final DriverRepository driverRepository;


    @Autowired
    public TruckService(TruckRepository truckRepository, CityRepository cityRepository,
                        TruckMapper truckMapper, DriverRepository driverRepository) {
        this.truckRepository = truckRepository;
        this.cityRepository = cityRepository;
        this.truckMapper = truckMapper;
        this.driverRepository = driverRepository;
    }

    public Long create(Truck truck) {
        City city = cityRepository.getById(truck.getCurrentCity().getId());
        truck.setCurrentCity(city);

        return truckRepository.saveAndFlush(truck).getId();
    }

    public List<TruckDTO> getAll() {
        List<Truck> truckEntities = truckRepository.findByIsDeletedNull();
        return truckMapper.mapToDtoList(truckEntities);
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
        truckMapper.updateEntity(truck, entityToUpdate);
        entityToUpdate.setCurrentCity(city);
        truckRepository.saveAndFlush(entityToUpdate);

        return entityToUpdate.getId();
    }

    public Long delete(Long id) {

        Truck truck = truckRepository.findById(id)
                .orElseThrow(() -> new TruckNotFoundException(String.format("Truck with id = %s doesn't exist", id)));
        List<Driver> drivers = driverRepository.getAllByTruckId(truck.getId());
        drivers.forEach(driver -> driver.setTruck(null));
        truck.setIsDeleted(LocalDateTime.now());
        return truck.getId();
    }

    @Override
    public List<TruckDTO> getAvailableTrucks() {
        List<Truck> trucks = new ArrayList<>();
        if (truckRepository.getTrucksAvailable().isPresent())
            trucks = truckRepository.getTrucksAvailable().get();
        return truckMapper.mapToDtoList(trucks);
    }

    @Override
    public List<TruckDTO> getTrucksForOrder(int dispatchWeight) {
        Optional<List<Truck>> trucksForOrder = truckRepository.getTrucksForOrder(dispatchWeight);
        if (!trucksForOrder.isPresent())
            throw new ApiException(500, "Oops, something has broken. Try again.");
        List<Truck> trucks = trucksForOrder.get();
        if (trucks.size() == 0) {
            throw new ApiException(400, "There are no trucks can carry the weight of your shipments. Please change your request");
        }
        return truckMapper.mapToDtoList(trucks);
    }
}
