package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.dto.CityDTO;
import com.tsystem.logisticsbe.dto.TruckDTO;
import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.entity.Truck;
import com.tsystem.logisticsbe.exception.TruckNotFoundException;
import com.tsystem.logisticsbe.exception.WrongInputDataException;
import com.tsystem.logisticsbe.factory.TruckDTOFactory;
import com.tsystem.logisticsbe.mapper.TruckMapper;
import com.tsystem.logisticsbe.repository.CityRepository;
import com.tsystem.logisticsbe.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TruckService {

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

    @Transactional
    public Long create(TruckDTO truckDTO) {
        verifyTruckData(truckDTO);
        CityDTO cityDTO = truckDTO.getCityDTO();
        City city = cityRepository.findById(cityDTO.getId())
                .orElseThrow(() -> new WrongInputDataException(String.format("City with name %s does not exist", cityDTO.getCity())));
        Truck truck = truckMapper.mapToEntity(truckDTO);
        truck.setCurrentCity(city);
        return truckRepository.saveAndFlush(truck).getId();
    }

    public List<TruckDTO> getAll() {
        List<Truck> truckEntities = truckRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return truckDTOFactory.createDefaultListTruckDTO(truckEntities);
    }

    public TruckDTO getByID(Long id) {
        Truck entity = truckRepository.findById(id)
                .orElseThrow(() -> new TruckNotFoundException(String.format("Truck with id %s doesn't exist", id)));
        return truckDTOFactory.createDefault(entity);
    }

    @Transactional
    public TruckDTO update(Long id, TruckDTO truckDTO) {
        verifyTruckData(truckDTO);
        CityDTO cityDTO = truckDTO.getCityDTO();
        Long cityId = cityDTO.getId();
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new WrongInputDataException(String.format("City with name %s does not exist", cityDTO.getCity())));
        Truck entityToUpdate = truckRepository.findById(id).get();
        truckMapper.updateFromDTO(truckDTO, entityToUpdate);
        entityToUpdate.setCurrentCity(city);
        truckRepository.saveAndFlush(entityToUpdate);
        return truckDTO;
    }

    public Long delete(Long id) {
        if (truckRepository.existsById(id)) {
            truckRepository.deleteById(id);
            return id;
        } else
            throw new TruckNotFoundException(String.format("Truck with id = %s doesn't exist", id));
    }

    private void verifyTruckData(TruckDTO truckDTO) {
        Integer loadCapacity = truckDTO.getLoadCapacity();
        if (loadCapacity < 0 | loadCapacity > 50)
            throw new WrongInputDataException("Load capacity must be from 0 to 50 tons");
        if (!truckDTO.getRegNumber().matches("^[A-Za-z]{2}[0-9]{5}$"))
            throw new WrongInputDataException("Registration number must meet format requirement, ex. 'AA12345'");
    }
}
