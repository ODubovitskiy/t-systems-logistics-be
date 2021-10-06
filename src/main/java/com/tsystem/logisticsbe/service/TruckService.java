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
        City city = cityRepository.getById(truckDTO.getCityDTO().getId());
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
        return truckMapper.mapToDTO(entity);
    }

    @Transactional
    public TruckDTO update(Long id, TruckDTO truckDTO) {

        City city = cityRepository.getById(truckDTO.getCityDTO().getId());
        Truck entityToUpdate = truckRepository.getById(id);
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

}
