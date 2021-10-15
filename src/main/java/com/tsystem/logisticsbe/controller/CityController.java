package com.tsystem.logisticsbe.controller;

import com.tsystem.logisticsbe.controller.api.ICityController;
import com.tsystem.logisticsbe.dto.CityDTO;
import com.tsystem.logisticsbe.mapper.CityMapper;
import com.tsystem.logisticsbe.service.api.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController implements ICityController {

    private final ICityService cityService;
    private final CityMapper cityMapper;

    @Autowired
    public CityController(ICityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @Override
    public List<CityDTO> getAll() {
        return cityMapper.mapToDtoList(cityService.getAll());
    }
}
