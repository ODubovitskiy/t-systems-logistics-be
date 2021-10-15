package com.tsystem.logisticsbe.service;

import com.tsystem.logisticsbe.entity.City;
import com.tsystem.logisticsbe.repository.CityRepository;
import com.tsystem.logisticsbe.service.api.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService implements ICityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }
}
