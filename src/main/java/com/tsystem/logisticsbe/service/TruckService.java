package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import com.tsystem.logisticsbe.exception.ApiException;
import com.tsystem.logisticsbe.exception.TruckNotFoundException;
import com.tsystem.logisticsbe.mapper.TruckMapper;
import com.tsystem.logisticsbe.repository.CityRepository;
import com.tsystem.logisticsbe.repository.DriverRepository;
import com.tsystem.logisticsbe.repository.TruckRepository;
import com.tsystem.logisticsbe.service.api.ITruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Truck entity = truckRepository.findTruckByIdAndIsDeletedNull(id)
                .orElseThrow(() -> new TruckNotFoundException(String.format("Truck with id %s doesn't exist", id)));
        return truckMapper.mapToDTO(entity);
    }

    public Long update(Long id, Truck truck) {
        Optional<Truck> truckOptional = truckRepository.findTruckByIdAndIsDeletedNull(id);
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
        Truck truck = truckRepository.findTruckByIdAndIsDeletedNull(id)
                .orElseThrow(() -> new TruckNotFoundException(String.format("Truck with id = %s doesn't exist", id)));
        List<Driver> drivers = driverRepository.getAllByTruckId(truck.getId());
        drivers.forEach(driver -> {
            if (driver.getStatus() == DriverStatus.DRIVING | driver.getStatus() == DriverStatus.ON_SHIFT)
                throw new ApiException(HttpStatus.BAD_REQUEST, "This truck cannot be deleted as there are drivers currently using it.");
            driver.setTruck(null);
        });
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
    public Set<TruckDTO> getTrucksForOrder(List<City> citiesToStart, Integer shipmentsTotalWeight) {
        Optional<Set<Truck>> trucksForOrder = truckRepository.getTrucksForOrder(citiesToStart, shipmentsTotalWeight);
        if (!trucksForOrder.isPresent())
            throw new ApiException(HttpStatus.BAD_REQUEST, "Oops, something has broken. Try again.");
        Set<Truck> trucks = trucksForOrder.get();
        if (trucks.size() == 0)
            throw new ApiException(HttpStatus.BAD_REQUEST, "There are no trucks can deliver your shipments. Please change your request");

        return truckMapper.mapToDtoSet(trucks);
    }
}
