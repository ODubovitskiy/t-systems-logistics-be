package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Driver;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.entity.domain.DriverStatus;
import com.tsystem.logisticsbe.entity.domain.TruckStatus;
import com.tsystem.logisticsbe.exception.ApiException;
import com.tsystem.logisticsbe.kafka.CustomKafkaProducer;
import com.tsystem.logisticsbe.mapper.TruckMapper;
import com.tsystem.logisticsbe.repository.CityRepository;
import com.tsystem.logisticsbe.repository.DriverRepository;
import com.tsystem.logisticsbe.repository.TruckRepository;
import com.tsystem.logisticsbe.service.api.ITruckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TruckService implements ITruckService {

    private final TruckRepository truckRepository;
    private final CityRepository cityRepository;
    private final TruckMapper truckMapper;
    private final DriverRepository driverRepository;
    private final CustomKafkaProducer customKafkaProducer;

    public TruckDTO create(Truck truck) {
        if (truckRepository.findByRegNumberAndIsDeletedNull(truck.getRegNumber()) != null)
            throw new ApiException(HttpStatus.BAD_REQUEST, "Truck with this registration number already exist");
        City city = cityRepository.getById(truck.getCurrentCity().getId());
        truck.setCurrentCity(city);
        TruckDTO truckDTO = truckMapper.mapToDTO(truckRepository.saveAndFlush(truck));
        List<Truck> truckEntities = truckRepository.findByIsDeletedNull();
        List<TruckDTO> truckDTOList = truckMapper.mapToDtoList(truckEntities);
        for (TruckDTO dto : truckDTOList) {
            customKafkaProducer.send(dto);
        }
        return truckDTO;
    }

    public List<TruckDTO> getAll() {
        List<Truck> truckEntities = truckRepository.findByIsDeletedNull();
        List<TruckDTO> truckDTOList = truckMapper.mapToDtoList(truckEntities);
        for (TruckDTO truckDTO : truckDTOList) {
            customKafkaProducer.send(truckDTO);
        }
        return truckDTOList;
    }

    public TruckDTO getByID(Long id) {
        Truck entity = truckRepository.findTruckByIdAndIsDeletedNull(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        String.format("Truck with id %s doesn't exist", id)));
        return truckMapper.mapToDTO(entity);
    }

    public Long update(Long id, Truck truck) {
        Truck truckToUpdate = truckRepository.findTruckByIdAndIsDeletedNull(id)
                .orElseThrow(()
                        -> new ApiException(HttpStatus.NOT_FOUND,
                        String.format("Truck with id = %s doesn't exist", id)));
        City city = cityRepository.getById(truck.getCurrentCity().getId());

        if (truck.getStatus() == TruckStatus.BROKEN) {
            List<Driver> drivers = driverRepository.getAllByTruckId(truck.getId());
            drivers.forEach(driver -> {
                driver.setStatus(DriverStatus.REST);
                driver.setTruck(null);
            });
        }
        truckMapper.updateEntity(truck, truckToUpdate);
        truckToUpdate.setCurrentCity(city);
        truckRepository.saveAndFlush(truckToUpdate);
        List<Truck> truckEntities = truckRepository.findByIsDeletedNull();

        List<TruckDTO> truckDTOList = truckMapper.mapToDtoList(truckEntities);
        for (TruckDTO dto : truckDTOList) {
            customKafkaProducer.send(dto);
        }
        return truckToUpdate.getId();
    }

    public Long delete(Long id) {
        Truck truck = truckRepository.findTruckByIdAndIsDeletedNull(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, String.format("Truck with id = %s doesn't exist", id)));
        if (truck.getStatus() == TruckStatus.WORKING)
            throw new ApiException(HttpStatus.BAD_REQUEST, "You cannot delete a working truck");
        List<Driver> drivers = driverRepository.getAllByTruckId(truck.getId());
        drivers.forEach(driver -> {
            if (driver.getStatus() == DriverStatus.DRIVING | driver.getStatus() == DriverStatus.ON_SHIFT)
                throw new ApiException(HttpStatus.BAD_REQUEST, "This truck cannot be deleted as there are drivers currently using it.");
            driver.setTruck(null);
        });
        truck.setIsDeleted(LocalDateTime.now());
        List<Truck> truckEntities = truckRepository.findByIsDeletedNull();
        List<TruckDTO> truckDTOList = truckMapper.mapToDtoList(truckEntities);
        for (TruckDTO dto : truckDTOList) {
            customKafkaProducer.send(dto);
        }
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
        Set<Truck> trucks = truckRepository.getTrucksForOrder(citiesToStart, shipmentsTotalWeight)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Oops, something has broken. Try again."));
        if (trucks.size() == 0)
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "There are no trucks can deliver your shipments. Please change your request");

        return truckMapper.mapToDtoSet(trucks);
    }
}
